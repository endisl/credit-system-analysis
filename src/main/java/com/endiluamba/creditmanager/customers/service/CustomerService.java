package com.endiluamba.creditmanager.customers.service;

import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.exception.CustomerAlreadyExistsException;
import com.endiluamba.creditmanager.customers.mapper.CustomerMapper;
import com.endiluamba.creditmanager.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final static CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public MessageDTO create(CustomerDTO customerToCreateDTO) {
        verifyIfExists(customerToCreateDTO.getCpf(),customerToCreateDTO.getEmail());
        Customer customerToCreate = customerMapper.toModel(customerToCreateDTO);
        Customer createdCustomer = customerRepository.save(customerToCreate);
        return creationMessage(createdCustomer);
    }

    private void verifyIfExists(String cpf, String email) {
        Optional<Customer> foundCustomer = customerRepository.findByCpfOrEmail(cpf, email);
        if (foundCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(cpf, email);
        }
    }

    private MessageDTO creationMessage(Customer createdCustomer) {
        String createdName = createdCustomer.getName();
        Long createdId = createdCustomer.getId();
        String createdCustomerMessage = String.format("Customer %s with ID %s successfully created", createdName, createdId);
        return MessageDTO.builder()
                .message(createdCustomerMessage)
                .build();
    }
}
