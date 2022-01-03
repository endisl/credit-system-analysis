package com.endiluamba.creditmanager.loans.service;

import com.endiluamba.creditmanager.customers.service.CustomerService;
import com.endiluamba.creditmanager.loans.mapper.LoanMapper;
import com.endiluamba.creditmanager.loans.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
