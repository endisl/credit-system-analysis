package com.endiluamba.creditmanager.customers.exception;

import javax.persistence.EntityExistsException;

public class CustomerAlreadyExistsException extends EntityExistsException {

    public CustomerAlreadyExistsException(String cpf, String email) {
        super(String.format("User with CPF %s or email %s already exists!", cpf, email));
    }
}
