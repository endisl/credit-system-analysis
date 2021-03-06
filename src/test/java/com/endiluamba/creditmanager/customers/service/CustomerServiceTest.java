package com.endiluamba.creditmanager.customers.service;

import com.endiluamba.creditmanager.customers.builder.CustomerDTOBuilder;
import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.enums.Role;
import com.endiluamba.creditmanager.customers.exception.CustomerAlreadyExistsException;
import com.endiluamba.creditmanager.customers.exception.CustomerNotFoundException;
import com.endiluamba.creditmanager.customers.mapper.CustomerMapper;
import com.endiluamba.creditmanager.customers.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private final static CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    private CustomerDTOBuilder customerDTOBuilder;

    @BeforeEach
    void setUp() {
        customerDTOBuilder = CustomerDTOBuilder.builder().build();
    }

    @Test
    void whenNewCustomerIsInformedThenItShouldBeCreated() {
        CustomerDTO expectedCreatedCustomerDTO = customerDTOBuilder.buildCustomerDTO();
        Customer expectedCreatedCustomer = customerMapper.toModel(expectedCreatedCustomerDTO);
        expectedCreatedCustomer.setRole(Role.USER);
        String expectedCreationMessage = "Customer Endi Luamba with ID 1 successfully created";
        String expectedCustomerCpf = expectedCreatedCustomerDTO.getCpf();
        String expectedCustomerEmail = expectedCreatedCustomerDTO.getEmail();

        when(customerRepository.findByCpfOrEmail(expectedCustomerCpf, expectedCustomerEmail)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(expectedCreatedCustomer.getPassword())).thenReturn(expectedCreatedCustomer.getPassword());
        when(customerRepository.save(expectedCreatedCustomer)).thenReturn(expectedCreatedCustomer);

        MessageDTO creationMessage = customerService.create(expectedCreatedCustomerDTO);

        assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
    }

    @Test
    void whenExistingCustomerIsInformedToCreateThenAnExceptionShouldBeThrown() {
        CustomerDTO expectedDuplicatedCustomerDTO = customerDTOBuilder.buildCustomerDTO();
        Customer expectedDuplicatedCustomer = customerMapper.toModel(expectedDuplicatedCustomerDTO);
        String expectedCustomerCpf = expectedDuplicatedCustomerDTO.getCpf();
        String expectedCustomerEmail = expectedDuplicatedCustomerDTO.getEmail();

        when(customerRepository.findByCpfOrEmail(expectedCustomerCpf, expectedCustomerEmail)).thenReturn(Optional.of(expectedDuplicatedCustomer));

        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.create(expectedDuplicatedCustomerDTO));
    }

    @Test
    void whenExistingCustomerIsInformedThenItShouldBeUpdated() {
        CustomerDTO expectedUpdatedCustomerDTO = customerDTOBuilder.buildCustomerDTO();
        expectedUpdatedCustomerDTO.setName("Endi Tequeiro");
        Customer expectedUpdatedCustomer = customerMapper.toModel(expectedUpdatedCustomerDTO);
        expectedUpdatedCustomer.setRole(Role.USER);
        String expectedUpdateMessage = "Customer Endi Tequeiro with ID 1 successfully updated";
        var expectedUpdatedCustomerId = expectedUpdatedCustomerDTO.getId();

        when(customerRepository.findById(expectedUpdatedCustomerId)).thenReturn(Optional.of(expectedUpdatedCustomer));
        when(passwordEncoder.encode(expectedUpdatedCustomer.getPassword())).thenReturn(expectedUpdatedCustomer.getPassword());
        when(customerRepository.save(expectedUpdatedCustomer)).thenReturn(expectedUpdatedCustomer);

        MessageDTO updateMessage = customerService.update(expectedUpdatedCustomerId, expectedUpdatedCustomerDTO);

        assertThat(expectedUpdateMessage, is(equalTo(updateMessage.getMessage())));
    }

    @Test
    void whenNotExistingCustomerIsInformedThenAnExceptionShouldBeThrown() {
        CustomerDTO expectedUpdatedCustomerDTO = customerDTOBuilder.buildCustomerDTO();
        expectedUpdatedCustomerDTO.setName("Endi Tequeiro");
        var expectedUpdatedCustomerId = expectedUpdatedCustomerDTO.getId();

        when(customerRepository.findById(expectedUpdatedCustomerId)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.update(expectedUpdatedCustomerId, expectedUpdatedCustomerDTO));
    }
}
