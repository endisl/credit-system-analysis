package com.endiluamba.creditmanager.loans.controller;

import com.endiluamba.creditmanager.customers.dto.AuthenticatedUser;
import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.dto.LoanResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api("Loans management")
public interface LoanControllerDocs {

    @ApiOperation(value = "Loan creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Loan successfully created"),
            @ApiResponse(code = 400, message = "Required Field missing or Field validation rules violated")
    })
    LoanResponseDTO create(AuthenticatedUser authenticatedUser, LoanRequestDTO loanRequestDTO);
}
