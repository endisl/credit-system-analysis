package com.endiluamba.creditmanager.loans.exception;

import java.time.LocalDate;

public class InvalidLoanArgumentException extends Exception {

    public InvalidLoanArgumentException(int instalments) {
        super(String.format("Installments provided: %d is not valid. " +
                "The number of installments must be at least 1 and less than or equal to 60.", instalments));
    }
    public InvalidLoanArgumentException(LocalDate maxDate) {
        super(String.format("The date for paying the first installment must be valid and before %s.", maxDate));
    }
    public InvalidLoanArgumentException(int installments, LocalDate maxDate) {
        super(String.format("Installments provided: %d is not valid. " +
                "The number of installments must be at least 1 and less than or equal to 60." +
                "And the date for paying the first installment must be valid and before %s.", installments, maxDate));
    }
}
