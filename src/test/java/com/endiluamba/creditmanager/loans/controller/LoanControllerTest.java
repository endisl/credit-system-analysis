package com.endiluamba.creditmanager.loans.controller;

import com.endiluamba.creditmanager.customers.builder.CustomerDTOBuilder;
import com.endiluamba.creditmanager.customers.controller.CustomerController;
import com.endiluamba.creditmanager.customers.service.AuthenticationService;
import com.endiluamba.creditmanager.customers.service.CustomerService;
import com.endiluamba.creditmanager.loans.builder.LoanRequestDTOBuilder;
import com.endiluamba.creditmanager.loans.builder.LoanResponseDTOBuilder;
import com.endiluamba.creditmanager.loans.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
public class LoanControllerTest {

    private static final String LOANS_API_URL_PATH = "/api/v1/loans";

    private MockMvc mockMvc;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    private LoanRequestDTOBuilder loanRequestDTOBuilder;

    private LoanResponseDTOBuilder loanResponseDTOBuilder;

    @BeforeEach
    void setUp() {
        loanRequestDTOBuilder = LoanRequestDTOBuilder.builder().build();
        loanResponseDTOBuilder = LoanResponseDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(loanController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }
}
