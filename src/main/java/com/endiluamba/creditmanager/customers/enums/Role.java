package com.endiluamba.creditmanager.customers.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("Admin"),
    USER("User");

    private final String description;
}
