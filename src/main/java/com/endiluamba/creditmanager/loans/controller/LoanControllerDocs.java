package com.endiluamba.creditmanager.loans.controller;

import com.endiluamba.creditmanager.customers.dto.AuthenticatedUser;
import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.dto.LoanResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Loans management")
public interface LoanControllerDocs {

    @ApiOperation(value = "Loan creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Loan successfully created"),
            @ApiResponse(code = 400, message = "Required Field missing or Field validation rules violated")
    })
    LoanResponseDTO create(AuthenticatedUser authenticatedUser, LoanRequestDTO loanRequestDTO);

    @ApiOperation(value = "Loan find by id and customer operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Loan successfully found"),
            @ApiResponse(code = 404, message = "Loan not found error")
    })
    LoanResponseDTO findByIdAndCustomer(AuthenticatedUser authenticatedUser, Long loanId);
}
