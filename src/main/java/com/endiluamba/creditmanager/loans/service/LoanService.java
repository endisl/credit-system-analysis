package com.endiluamba.creditmanager.loans.service;

import com.endiluamba.creditmanager.customers.dto.AuthenticatedUser;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.service.CustomerService;
import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.dto.LoanResponseDTO;
import com.endiluamba.creditmanager.loans.dto.LoansListResponseDTO;
import com.endiluamba.creditmanager.loans.dto.MessageDTO;
import com.endiluamba.creditmanager.loans.entity.Loan;
import com.endiluamba.creditmanager.loans.exception.FirstInstallmentDateIsBeyondMaxDateException;
import com.endiluamba.creditmanager.loans.exception.InstallmentsLimitExceededException;
import com.endiluamba.creditmanager.loans.exception.LoanAlreadyExistsException;
import com.endiluamba.creditmanager.loans.exception.LoanNotFoundException;
import com.endiluamba.creditmanager.loans.mapper.LoanMapper;
import com.endiluamba.creditmanager.loans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.endiluamba.creditmanager.loans.Utils.MessageDTOUtils.creationMessage;

@Service
public class LoanService {

    private final static LoanMapper loanMapper = LoanMapper.INSTANCE;

    private LoanRepository loanRepository;

    private CustomerService customerService;

    @Autowired
    public LoanService(LoanRepository loanRepository, CustomerService customerService) {
        this.loanRepository = loanRepository;
        this.customerService = customerService;
    }

    public MessageDTO create(AuthenticatedUser authenticatedUser, LoanRequestDTO loanRequestDTO) {
        Customer foundAuthenticatedCustomer = customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername());
        verifyIfLoanIsAlreadySubmitted(foundAuthenticatedCustomer, loanRequestDTO);
        Loan loanToCreate = loanMapper.toModel(loanRequestDTO);

        verifyIfInstallmentsValueIsGreaterThan60(loanToCreate.getInstallments());

        verifyIfFirstInstallmentDateIsBeyondMaxDate(loanToCreate.getFirstInstallmentDate());

        String status = "Submitted";
        loanToCreate.setCustomer(foundAuthenticatedCustomer);
        loanToCreate.setStatus(status);
        Loan createdLoan = loanRepository.save(loanToCreate);

        return creationMessage(createdLoan);
    }

    public LoanResponseDTO findByIdAndCustomer(AuthenticatedUser authenticatedUser, Long loanId) {
        Customer foundAuthenticatedCustomer = customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername());

        Loan foundLoan = loanRepository.findByIdAndCustomer(loanId, foundAuthenticatedCustomer)
                .orElseThrow(() -> new LoanNotFoundException(loanId));

        LoanResponseDTO loanResponseDTO = loanMapper.toDTO(foundLoan);
        loanResponseDTO.setEmail(foundLoan.getCustomer().getEmail());
        loanResponseDTO.setIncome(foundLoan.getCustomer().getIncome());

        return loanResponseDTO;
    }

    public List<LoansListResponseDTO> findAllByCustomer(AuthenticatedUser authenticatedUser) {
        Customer foundAuthenticatedCustomer = customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername());
        return loanRepository.findAllByCustomer(foundAuthenticatedCustomer)
                .stream()
                .map(loanMapper::toListDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteByIdAndCustomer(AuthenticatedUser authenticatedUser, Long loanId) {
        Customer foundAuthenticatedCustomer = customerService.verifyAndGetCustomerIfExists(authenticatedUser.getUsername());
        Loan foundLoanToDelete = verifyAndGetIfExists(loanId, foundAuthenticatedCustomer);
        loanRepository.deleteByIdAndCustomer(foundLoanToDelete.getId(), foundAuthenticatedCustomer);
    }

    private Loan verifyAndGetIfExists(Long loanId, Customer foundAuthenticatedCustomer) {
        return loanRepository.findByIdAndCustomer(loanId, foundAuthenticatedCustomer)
                .orElseThrow(() -> new LoanNotFoundException(loanId));
    }

    private void verifyIfLoanIsAlreadySubmitted(Customer customer, LoanRequestDTO loanRequestDTO) {
        Double loanAmount = loanRequestDTO.getLoanAmount();
        Integer installments = loanRequestDTO.getInstallments();
        LocalDate firstInstallmentDate = loanRequestDTO.getFirstInstallmentDate();

        loanRepository.findByLoanAmountAndInstallmentsAndFirstInstallmentDateAndCustomer(loanAmount, installments, firstInstallmentDate, customer)
                .ifPresent(duplicatedLoan -> {
                    throw new LoanAlreadyExistsException(loanAmount, installments, firstInstallmentDate, customer.getName());
                });
    }

    private void verifyIfInstallmentsValueIsGreaterThan60(int installments) {
        if (installments > 60)
            throw new InstallmentsLimitExceededException();
    }

    private void verifyIfFirstInstallmentDateIsBeyondMaxDate(LocalDate firstInstallmentDate) {
        int maxMonths = 3;
        LocalDate createdLoanDate = LocalDate.now();
        LocalDate maxDate = createdLoanDate.plusMonths(maxMonths);

        if (firstInstallmentDate.isAfter(maxDate))
            throw new FirstInstallmentDateIsBeyondMaxDateException(maxDate);
    }
}
