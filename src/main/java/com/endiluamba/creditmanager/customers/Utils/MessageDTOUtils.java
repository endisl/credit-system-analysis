package com.endiluamba.creditmanager.customers.Utils;

import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import com.endiluamba.creditmanager.customers.entity.Customer;

public class MessageDTOUtils {

    public static MessageDTO creationMessage(Customer createdCustomer) {
        return returnMessage(createdCustomer, "created");
    }

    public static MessageDTO updateMessage(Customer updatedCustomer) {
        return returnMessage(updatedCustomer, "updated");
    }

    public static MessageDTO returnMessage(Customer updatedCustomer, String operation) {
        String createdName = updatedCustomer.getName();
        Long createdId = updatedCustomer.getId();
        String createdCustomerMessage = String.format("Customer %s with ID %s successfully %s", createdName, createdId, operation);
        return MessageDTO.builder()
                .message(createdCustomerMessage)
                .build();
    }
}
