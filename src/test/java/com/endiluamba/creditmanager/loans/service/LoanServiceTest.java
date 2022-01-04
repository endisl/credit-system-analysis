package com.endiluamba.creditmanager.loans.service;

import com.endiluamba.creditmanager.customers.dto.AuthenticatedUser;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.service.CustomerService;
import com.endiluamba.creditmanager.loans.builder.LoanRequestDTOBuilder;
import com.endiluamba.creditmanager.loans.builder.LoanResponseDTOBuilder;
import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.dto.LoanResponseDTO;
import com.endiluamba.creditmanager.loans.entity.Loan;
import com.endiluamba.creditmanager.loans.exception.LoanAlreadyExistsException;
import com.endiluamba.creditmanager.loans.exception.LoanNotFoundException;
import com.endiluamba.creditmanager.loans.mapper.LoanMapper;
import com.endiluamba.creditmanager.loans.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    private final static LoanMapper loanMapper = LoanMapper.INSTANCE;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private LoanService loanService;

    private LoanRequestDTOBuilder loanRequestDTOBuilder;

    private LoanResponseDTOBuilder loanResponseDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        loanRequestDTOBuilder = LoanRequestDTOBuilder.builder().build();
        loanResponseDTOBuilder = LoanResponseDTOBuilder.builder().build();
        authenticatedUser = new AuthenticatedUser("endi@web.com", "1234", "ADMIN");
    }

    @Test
    void whenNewLoanIsInformedThenItShouldBeCreated() {
        LoanRequestDTO expectedLoanToCreateDTO = loanRequestDTOBuilder.buildLoanRequestDTO();
        LoanResponseDTO expectedCreatedLoanDTO = loanResponseDTOBuilder.buildLoanResponseDTO();
        Loan expectedCreatedLoan = loanMapper.toModel(expectedCreatedLoanDTO);

        when(customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername())).thenReturn(new Customer());
        when(loanRepository.findByLoanAmountAndInstallmentsAndFirstInstallmentDateAndCustomer(
                eq(expectedLoanToCreateDTO.getLoanAmount()),
                eq(expectedLoanToCreateDTO.getInstallments()),
                eq(expectedLoanToCreateDTO.getFirstInstallmentDate()),
                any(Customer.class))).thenReturn(Optional.empty());
        when(loanRepository.save(any(Loan.class))).thenReturn(expectedCreatedLoan);

        LoanResponseDTO createdLoanResponseDTO = loanService.create(authenticatedUser, expectedLoanToCreateDTO);

        assertThat(createdLoanResponseDTO, is(equalTo(expectedCreatedLoanDTO)));
    }

    @Test
    void whenExistingLoanIsInformedToCreateThenAnExceptionShouldBeThrown() {
        LoanRequestDTO expectedLoanToCreateDTO = loanRequestDTOBuilder.buildLoanRequestDTO();
        LoanResponseDTO expectedCreatedLoanDTO = loanResponseDTOBuilder.buildLoanResponseDTO();
        Loan expectedDuplicatedLoan = loanMapper.toModel(expectedCreatedLoanDTO);

        when(customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername())).thenReturn(new Customer());
        when(loanRepository.findByLoanAmountAndInstallmentsAndFirstInstallmentDateAndCustomer(
                eq(expectedLoanToCreateDTO.getLoanAmount()),
                eq(expectedLoanToCreateDTO.getInstallments()),
                eq(expectedLoanToCreateDTO.getFirstInstallmentDate()),
                any(Customer.class))).thenReturn(Optional.of(expectedDuplicatedLoan));

        assertThrows(LoanAlreadyExistsException.class, () -> loanService.create(authenticatedUser, expectedLoanToCreateDTO));
    }

    @Test
    void whenExistingLoanIsInformedThenItShouldBeReturned() {
        LoanRequestDTO expectedLoanToFindDTO = loanRequestDTOBuilder.buildLoanRequestDTO();
        LoanResponseDTO expectedFoundLoanDTO = loanResponseDTOBuilder.buildLoanResponseDTO();
        Loan expectedFoundLoan = loanMapper.toModel(expectedFoundLoanDTO);

        when(customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername())).thenReturn(new Customer());
        when(loanRepository.findByIdAndCustomer(
                eq(expectedLoanToFindDTO.getId()),
                any(Customer.class))).thenReturn(Optional.of(expectedFoundLoan));

        LoanResponseDTO foundLoanDTO = loanService.findByIdAndCustomer(authenticatedUser, expectedLoanToFindDTO.getId());

        assertThat(foundLoanDTO, is(equalTo(expectedFoundLoanDTO)));
    }

    @Test
    void whenNotExistingLoanIsInformedThenAnExceptionShouldBeThrown() {
        LoanRequestDTO expectedLoanToFindDTO = loanRequestDTOBuilder.buildLoanRequestDTO();

        when(customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername())).thenReturn(new Customer());
        when(loanRepository.findByIdAndCustomer(eq(expectedLoanToFindDTO.getId()), any(Customer.class))).thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class, () -> loanService.findByIdAndCustomer(authenticatedUser, expectedLoanToFindDTO.getId()));
    }

    @Test
    void whenListLoansIsCalledThenItShouldBeReturned() {
        LoanResponseDTO expectedFoundLoanDTO = loanResponseDTOBuilder.buildLoanResponseDTO();
        Loan expectedFoundLoan = loanMapper.toModel(expectedFoundLoanDTO);

        when(customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername())).thenReturn(new Customer());
        when(loanRepository.findAllByCustomer(
                any(Customer.class))).thenReturn(Collections.singletonList(expectedFoundLoan));

        List<LoanResponseDTO> returnedLoansResponseList = loanService.findAllByCustomer(authenticatedUser);

        assertThat(returnedLoansResponseList.size(), is(1));
        assertThat(returnedLoansResponseList.get(0), is(equalTo(expectedFoundLoanDTO)));
    }

    @Test
    void whenListLoansIsCalledThenAnEmptyListShouldBeReturned() {
        when(customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername())).thenReturn(new Customer());
        when(loanRepository.findAllByCustomer(
                any(Customer.class))).thenReturn(Collections.EMPTY_LIST);

        List<LoanResponseDTO> returnedLoansResponseList = loanService.findAllByCustomer(authenticatedUser);

        assertThat(returnedLoansResponseList.size(), is(0));
    }

    @Test
    void whenExistingLoanIdIsInformedThenItShouldBeDeleted() {
        LoanResponseDTO expectedLoanToDeleteDTO = loanResponseDTOBuilder.buildLoanResponseDTO();
        Loan expectedLoanToDelete = loanMapper.toModel(expectedLoanToDeleteDTO);

        when(customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername())).thenReturn(new Customer());
        when(loanRepository.findByIdAndCustomer(eq(expectedLoanToDeleteDTO.getId()), any(Customer.class)))
        .thenReturn(Optional.of(expectedLoanToDelete));

        doNothing().when(loanRepository).deleteByIdAndCustomer(eq(expectedLoanToDeleteDTO.getId()), any(Customer.class));

        loanService.deleteByIdAndCustomer(authenticatedUser, expectedLoanToDeleteDTO.getId());

        verify(loanRepository, times(1)).deleteByIdAndCustomer(eq(expectedLoanToDeleteDTO.getId()), any(Customer.class));
    }

    @Test
    void whenNotExistingLoanIdIsInformedThenAnExceptionShouldBeThrown() {
        LoanResponseDTO expectedLoanToDeleteDTO = loanResponseDTOBuilder.buildLoanResponseDTO();

        when(customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername())).thenReturn(new Customer());
        when(loanRepository.findByIdAndCustomer(eq(expectedLoanToDeleteDTO.getId()), any(Customer.class)))
                .thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class, () -> loanService.deleteByIdAndCustomer(authenticatedUser, expectedLoanToDeleteDTO.getId()));
    }
}
