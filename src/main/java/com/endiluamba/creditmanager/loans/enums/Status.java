package com.endiluamba.creditmanager.loans.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    SUBMITTED("Loan Submitted"),
    APPROVED("Loan Approved"),
    REJECTED("Loan Rejected");

    private final String description;
}
