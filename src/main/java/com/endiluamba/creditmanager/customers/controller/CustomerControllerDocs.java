package com.endiluamba.creditmanager.customers.controller;

import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.dto.JwtRequest;
import com.endiluamba.creditmanager.customers.dto.JwtResponse;
import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Customers management")
public interface CustomerControllerDocs {
    @ApiOperation(value = "Customer creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer successfully created"),
            @ApiResponse(code = 400, message = "Required Field missing or Field validation rules violated")
    })
    MessageDTO create(CustomerDTO customerToCreateDTO);

    @ApiOperation(value = "Customer update operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer successfully updated"),
            @ApiResponse(code = 400, message = "Required Field missing or Field validation rules violated")
    })
    MessageDTO update(Long id, CustomerDTO customerToUpdateDTO);

    @ApiOperation(value = "Customer authentication operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer successfully authenticated"),
            @ApiResponse(code = 404, message = "Customer not found")
    })
    JwtResponse createAuthenticationToken(JwtRequest jwtRequest);
}
