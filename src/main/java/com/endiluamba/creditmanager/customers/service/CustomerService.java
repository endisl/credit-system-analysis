package com.endiluamba.creditmanager.customers.service;

import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.exception.CustomerAlreadyExistsException;
import com.endiluamba.creditmanager.customers.exception.CustomerNotFoundException;
import com.endiluamba.creditmanager.customers.mapper.CustomerMapper;
import com.endiluamba.creditmanager.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.endiluamba.creditmanager.customers.Utils.MessageDTOUtils.creationMessage;
import static com.endiluamba.creditmanager.customers.Utils.MessageDTOUtils.updateMessage;

@Service
public class CustomerService {

    private final static CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    private CustomerRepository customerRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MessageDTO create(CustomerDTO customerToCreateDTO) {
        verifyIfExists(customerToCreateDTO.getCpf(),customerToCreateDTO.getEmail());
        Customer customerToCreate = customerMapper.toModel(customerToCreateDTO);
        customerToCreate.setPassword(passwordEncoder.encode(customerToCreate.getPassword()));
        Customer createdCustomer = customerRepository.save(customerToCreate);
        return creationMessage(createdCustomer);
    }

    public MessageDTO update(Long id, CustomerDTO customerToUpdateDTO) {
        Customer foundCustomer = verifyAndGetIfExists(id);

        customerToUpdateDTO.setId(foundCustomer.getId());
        Customer customerToUpdate = customerMapper.toModel(customerToUpdateDTO);
        customerToUpdate.setPassword(passwordEncoder.encode(customerToUpdate.getPassword()));
        customerToUpdate.setCreatedDate(foundCustomer.getCreatedDate());

        Customer updatedCustomer = customerRepository.save(customerToUpdate);
        return updateMessage(updatedCustomer);
    }

    public void delete(Long id) {
        verifyAndGetIfExists(id);
        customerRepository.deleteById(id);
    }

    private Customer verifyAndGetIfExists(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private void verifyIfExists(String cpf, String email) {
        Optional<Customer> foundCustomer = customerRepository.findByCpfOrEmail(cpf, email);
        if (foundCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(cpf, email);
        }
    }

    public Customer verifyAndGetCustomerIfExists(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(email));
    }
}
