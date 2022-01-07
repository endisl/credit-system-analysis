package com.endiluamba.creditmanager.customers.controller;

import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.dto.JwtRequest;
import com.endiluamba.creditmanager.customers.dto.JwtResponse;
import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import com.endiluamba.creditmanager.customers.service.AuthenticationService;
import com.endiluamba.creditmanager.customers.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController implements CustomerControllerDocs {

    private CustomerService customerService;
    private AuthenticationService authenticationService;

    @Autowired
    public CustomerController(CustomerService customerService, AuthenticationService authenticationService) {
        this.customerService = customerService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid CustomerDTO customerToCreateDTO) {
        return customerService.create(customerToCreateDTO);
    }

    @PostMapping(value = "/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
        return authenticationService.createAuthenticationToken(jwtRequest);
    }

    @PutMapping("/{id}")
    public MessageDTO update(@PathVariable Long id, @RequestBody @Valid CustomerDTO customerToUpdateDTO) {
        return customerService.update(id, customerToUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }
}
