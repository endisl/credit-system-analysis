package com.endiluamba.creditmanager.loans.dto;

import com.endiluamba.creditmanager.customer.entity.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {

    private Long id;

    @NotNull
    @NotEmpty
    private Double loanAmount;

    @NotNull
    @Max(60)
    private Integer installments;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate firstInstallmentDate;

    @NotNull
    @NotEmpty
    private Customer customer;
}
