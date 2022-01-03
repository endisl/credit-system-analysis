package com.endiluamba.creditmanager.customers.builder;

import com.endiluamba.creditmanager.customers.dto.JwtRequest;
import lombok.Builder;

@Builder
public class JwtRequestBuilder {

    @Builder.Default
    private String email = "endi@web.com";

    @Builder.Default
    private String password = "12345";

    public JwtRequest buildJwtRequest() {
        return new JwtRequest(email, password);
    }
}
