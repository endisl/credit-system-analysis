package com.endiluamba.creditmanager.loans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoansListResponseDTO {

    private Long id;

    private Double loanAmount;

    private Integer installments;

    private String status;
}
