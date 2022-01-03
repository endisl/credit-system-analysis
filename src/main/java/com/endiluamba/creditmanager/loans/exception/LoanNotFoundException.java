package com.endiluamba.creditmanager.loans.exception;

import javax.persistence.EntityNotFoundException;

public class LoanNotFoundException extends EntityNotFoundException {
    public LoanNotFoundException(Long loanId) {
        super(String.format("Loan with Id %s does not exist!", loanId));
    }
}
