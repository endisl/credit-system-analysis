package com.endiluamba.creditmanager.loans.builder;

import com.endiluamba.creditmanager.customers.builder.CustomerDTOBuilder;
import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.enums.Role;
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

    @Builder.Default
    private Customer customer = new Customer() {{
       setId(1L);
       setName("Endi Luamba");
       setCpf("99522425060");
       setRg("332211447");
       setEmail("endi@web.com");
       setIncome(1000.0);
       setAddress("123, Web, Dev, Javaland");
       setPassword("1234");
       setRole(Role.USER);
    }};

    private final CustomerDTO customerDTO = CustomerDTOBuilder.builder().build().buildCustomerDTO();

    public LoanResponseDTO buildLoanResponseDTO() {
        return new LoanResponseDTO(id,
                loanAmount,
                installments,
                firstInstallmentDate,
                customerDTO.getEmail(),
                customerDTO.getIncome(),
                customer);
    }
}
