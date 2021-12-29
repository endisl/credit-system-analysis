package com.endiluamba.creditmanager.loans.service;

import com.endiluamba.creditmanager.loans.mapper.LoanMapper;
import com.endiluamba.creditmanager.loans.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LoanService {

    private final static LoanMapper customerMapper = LoanMapper.INSTANCE;

    private LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }
}
