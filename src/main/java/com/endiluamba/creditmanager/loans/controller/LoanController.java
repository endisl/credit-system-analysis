package com.endiluamba.creditmanager.loans.controller;

import com.endiluamba.creditmanager.loans.service.LoanService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController implements LoanControllerDocs {

    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @ApiOperation(value = "Return a message test")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Correct return")
    })
    @GetMapping
    public String hello() {
        return "Credit Manager GET operation in construction";
    }

}
