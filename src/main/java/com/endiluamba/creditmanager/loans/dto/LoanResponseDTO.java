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
public class LoanResponseDTO {

    private Long id;

    private Double loanAmount;

    private Integer installments;

    private LocalDate firstInstallmentDate;
}
