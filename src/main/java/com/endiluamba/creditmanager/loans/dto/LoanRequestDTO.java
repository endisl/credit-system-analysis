package com.endiluamba.creditmanager.loans.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestDTO {

    private Long id;

    @NotNull
    private Double loanAmount;

    @NotNull
    @Max(60)
    private Integer installments;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate firstInstallmentDate;
}