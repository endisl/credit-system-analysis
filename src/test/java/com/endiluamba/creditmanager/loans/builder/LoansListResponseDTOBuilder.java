package com.endiluamba.creditmanager.loans.builder;

import com.endiluamba.creditmanager.loans.dto.LoansListResponseDTO;
import lombok.Builder;

@Builder
public class LoansListResponseDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private Double loanAmount = 10_000.0;

    @Builder.Default
    private Integer installments = 30;

    @Builder.Default
    private String status = "Submitted";

    public LoansListResponseDTO buildLoansListResponseDTO() {
        return new LoansListResponseDTO(id,
                loanAmount,
                installments,
                status);
    }
}
