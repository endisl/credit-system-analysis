package com.endiluamba.creditmanager.customers.builder;

import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import lombok.Builder;

@Builder
public class CustomerDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Endi Luamba";

    @Builder.Default
    private String cpf = "99522425060"; //online generator

    @Builder.Default
    private String rg = "332211447"; //online generator

    @Builder.Default
    private double income = 1000.0;

    @Builder.Default
    private String address = "123, Web, Dev, Javaland";

    @Builder.Default
    private String email = "endi@web.com";

    @Builder.Default
    private String password = "1234";

    public CustomerDTO buildCustomerDTO() {
        return new CustomerDTO(id,
                name,
                cpf,
                rg,
                income,
                address,
                email,
                password);
    }

}
