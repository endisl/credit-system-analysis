package com.endiluamba.creditmanager.loans.exception;

import javax.persistence.PersistenceException;

public class InstallmentsLimitExceededException extends PersistenceException {
    public InstallmentsLimitExceededException() {
        super("The number of installments must be less than or equal to 60.");
    }

}
