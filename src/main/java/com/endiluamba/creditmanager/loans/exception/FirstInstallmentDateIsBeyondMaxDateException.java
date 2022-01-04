package com.endiluamba.creditmanager.loans.exception;

import javax.persistence.PersistenceException;
import java.time.LocalDate;

public class FirstInstallmentDateIsBeyondMaxDateException extends PersistenceException {
    public FirstInstallmentDateIsBeyondMaxDateException(LocalDate maxDateForFirstInstallment) {
        super(String.format("The date for paying the first installment must be before %s.",
                maxDateForFirstInstallment));
    }
}
