package com.endiluamba.creditmanager.loans.mapper;

import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.dto.LoanResponseDTO;
import com.endiluamba.creditmanager.loans.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    Loan toModel(LoanRequestDTO loanRequestDTO);

    Loan toModel(LoanResponseDTO loanResponseDTO);

    LoanResponseDTO toDTO(Loan loan);
}
