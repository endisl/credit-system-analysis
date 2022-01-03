package com.endiluamba.creditmanager.loans.builder;

import com.endiluamba.creditmanager.customers.builder.CustomerDTOBuilder;
import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.dto.LoanResponseDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class LoanResponseDTOBuilder {


    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private Double loanAmount = 10_000.0;

    @Builder.Default
    private Integer installments = 30;

    @Builder.Default
    private LocalDate firstInstallmentDate = LocalDate.of(2022, 01, 01);

    private final CustomerDTO customerDTO = CustomerDTOBuilder.builder().build().buildCustomerDTO();

    public LoanResponseDTO buildLoanResponseDTO() {
        return new LoanResponseDTO(id,
                loanAmount,
                installments,
                firstInstallmentDate
        );
    }
}
