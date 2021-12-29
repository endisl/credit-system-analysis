package com.endiluamba.creditmanager.loans.mapper;

import com.endiluamba.creditmanager.loans.dto.LoanDTO;
import com.endiluamba.creditmanager.loans.entity.Loan;
import org.mapstruct.factory.Mappers;

public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    Loan toModel(LoanDTO loanDTO);

    LoanDTO toDTO(Loan loan);
}
