package com.endiluamba.creditmanager.loans.Utils;

import com.endiluamba.creditmanager.loans.dto.MessageDTO;
import com.endiluamba.creditmanager.loans.entity.Loan;

public class MessageDTOUtils {

    public static com.endiluamba.creditmanager.loans.dto.MessageDTO creationMessage(Loan createdLoan) {
        return returnMessage(createdLoan, "submitted");
    }

    public static MessageDTO updateMessage(Loan updatedLoan) {
        return returnMessage(updatedLoan, "updated");
    }

    public static MessageDTO returnMessage(Loan loan, String operation) {
        Long createdId = loan.getId();
        String createdLoanMessage = String.format("Loan with ID %s successfully %s", createdId, operation);
        return MessageDTO.builder()
                .message(createdLoanMessage)
                .build();
    }
}
