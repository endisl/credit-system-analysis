package com.endiluamba.creditmanager.loans.controller;

import com.endiluamba.creditmanager.customers.dto.AuthenticatedUser;
import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.dto.LoanResponseDTO;
import com.endiluamba.creditmanager.loans.dto.MessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api("Loans management")
public interface LoanControllerDocs {

    @ApiOperation(value = "Loan creation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Loan successfully created"),
            @ApiResponse(code = 400, message = "Required Field missing or Field validation rules violated")
    })
    MessageDTO create(AuthenticatedUser authenticatedUser, LoanRequestDTO loanRequestDTO);

    @ApiOperation(value = "Loan find by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Loan by customer successfully found"),
            @ApiResponse(code = 404, message = "Loan not found error")
    })
    LoanResponseDTO findByIdAndCustomer(AuthenticatedUser authenticatedUser, Long loanId);

    @ApiOperation(value = "Loans list of an authenticated customer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Loans list successfully found")
    })
    List<LoanResponseDTO> findAllByCustomer(AuthenticatedUser authenticatedUser);


    @ApiOperation(value = "Loan delete by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Loan by customer successfully deleted"),
            @ApiResponse(code = 404, message = "Loan not found error")
    })
    void deleteByIdAndCustomer(AuthenticatedUser authenticatedUser, Long loanId);
}
