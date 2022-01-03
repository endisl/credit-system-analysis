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
    @Max(60) //This requirement must be verified in LoanService in order to get a customized exception message
    //otherwise the general message for code 400 in LoanControllerDocs or Postman will be displayed
    private Integer installments;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate firstInstallmentDate;
}
