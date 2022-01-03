package com.endiluamba.creditmanager.loans.service;

import com.endiluamba.creditmanager.customers.builder.CustomerDTOBuilder;
import com.endiluamba.creditmanager.customers.dto.AuthenticatedUser;
import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.mapper.CustomerMapper;
import com.endiluamba.creditmanager.customers.repository.CustomerRepository;
import com.endiluamba.creditmanager.customers.service.CustomerService;
import com.endiluamba.creditmanager.loans.builder.LoanRequestDTOBuilder;
import com.endiluamba.creditmanager.loans.builder.LoanResponseDTOBuilder;
import com.endiluamba.creditmanager.loans.mapper.LoanMapper;
import com.endiluamba.creditmanager.loans.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

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
}
