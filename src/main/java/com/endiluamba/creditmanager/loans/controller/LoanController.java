package com.endiluamba.creditmanager.loans.controller;

import com.endiluamba.creditmanager.customers.dto.AuthenticatedUser;
import com.endiluamba.creditmanager.loans.dto.LoanRequestDTO;
import com.endiluamba.creditmanager.loans.dto.LoanResponseDTO;
import com.endiluamba.creditmanager.loans.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController implements LoanControllerDocs {

    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanResponseDTO create(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                  @RequestBody @Valid LoanRequestDTO loanRequestDTO) {
        return loanService.create(authenticatedUser, loanRequestDTO);
    }

    @GetMapping("/{loanId}")
    public LoanResponseDTO findByIdAndCustomer(@AuthenticationPrincipal AuthenticatedUser authenticatedUser,
                                               @PathVariable Long loanId) {
        return loanService.findByIdAndCustomer(authenticatedUser, loanId);
    }
}
