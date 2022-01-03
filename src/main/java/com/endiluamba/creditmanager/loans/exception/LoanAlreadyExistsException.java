package com.endiluamba.creditmanager.loans.exception;

import com.endiluamba.creditmanager.customers.entity.Customer;

import javax.persistence.EntityExistsException;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class LoanAlreadyExistsException extends EntityExistsException {
    public LoanAlreadyExistsException(Double loanAmount, Integer installments, LocalDate firstInstallmentDate, String name) {
        super(String.format("Loan with amount %s to be paid in %s installments starting at %s for Customer %s is already submitted!",
                loanAmount, installments, firstInstallmentDate, name));
    }
}
