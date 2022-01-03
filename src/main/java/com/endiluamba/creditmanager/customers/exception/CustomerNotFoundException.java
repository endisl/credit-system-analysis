package com.endiluamba.creditmanager.customers.exception;

import javax.persistence.EntityNotFoundException;

public class CustomerNotFoundException extends EntityNotFoundException {
    public CustomerNotFoundException(Long id) {
        super(String.format("Customer with id %s does not exist!", id));
    }

    public CustomerNotFoundException(String email) {
        super(String.format("Customer with email %s does not exist!", email));
    }
}
