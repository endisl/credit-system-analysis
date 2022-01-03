package com.endiluamba.creditmanager.loans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
