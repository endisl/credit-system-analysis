package com.endiluamba.creditmanager.loans.exception;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

public class LoanAlreadyExistsException extends EntityExistsException {
    public LoanAlreadyExistsException(Double loanAmount, Integer installments, LocalDate firstInstallmentDate, String name) {
        super(String.format("Loan with amount %s to be paid in %s installments starting at %s for Customer %s is already submitted!",
                loanAmount, installments, firstInstallmentDate, name));
    }
}
