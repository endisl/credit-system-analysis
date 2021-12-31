package com.endiluamba.creditmanager.customers.controller;

import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api("customers management")
public interface CustomerControllerDocs {
    @ApiOperation(value = "Customer creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer successfully created"),
            @ApiResponse(code = 400, message = "Required Field missing or Field validation rules violated")
    })
    MessageDTO create(CustomerDTO customerToCreateDTO);

    @ApiOperation(value = "Customer deletion operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Customer successfully deleted"),
            @ApiResponse(code = 404, message = "Customer with informed id not found")
    })
    void delete(Long id);
}
