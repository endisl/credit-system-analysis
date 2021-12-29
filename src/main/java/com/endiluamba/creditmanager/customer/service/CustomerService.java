package com.endiluamba.creditmanager.customer.service;

import com.endiluamba.creditmanager.customer.mapper.CustomerMapper;
import com.endiluamba.creditmanager.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final static CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}
