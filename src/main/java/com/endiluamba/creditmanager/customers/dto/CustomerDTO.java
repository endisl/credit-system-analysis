package com.endiluamba.creditmanager.customers.dto;

import com.endiluamba.creditmanager.customers.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Long id;

    @NotNull
    @NotEmpty
    @Size(max = 255)
    private String name;

    @NotNull
    @NotEmpty
    @CPF
    private String cpf;

    @NotNull
    @NotEmpty
    private String rg;

    @NotNull
    private Double income;

    @NotNull
    @NotEmpty
    private String address;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
}
