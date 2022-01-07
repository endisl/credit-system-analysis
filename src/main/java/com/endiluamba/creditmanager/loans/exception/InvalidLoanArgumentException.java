package com.endiluamba.creditmanager.loans.exception;

import java.time.LocalDate;

public class InvalidLoanArgumentException extends Exception {
    public InvalidLoanArgumentException(int instalments) {
        super(String.format("Installments value: %d is not valid%nThe number of installments must be less than or equal to 60.", instalments));
    }
    public InvalidLoanArgumentException(LocalDate maxDate) {
        super(String.format("The date for paying the first installment must be before %s.",
                maxDate));
    }
    public InvalidLoanArgumentException(int installments, LocalDate maxDate) {
        super(String.format("Installments value: %d is not valid%nThe number of installments must be less than or equal to 60.%nAnd the date for paying " +
                        "the first installment must be before %s.", installments, maxDate));
    }
}
