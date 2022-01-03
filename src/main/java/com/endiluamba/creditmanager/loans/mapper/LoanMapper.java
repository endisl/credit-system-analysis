package com.endiluamba.creditmanager.loans.mapper;

import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.entity.Loan;
import org.mapstruct.factory.Mappers;

public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    Loan toModel(LoanRequestDTO loanRequestDTO);

    LoanRequestDTO toDTO(Loan loan);
}
