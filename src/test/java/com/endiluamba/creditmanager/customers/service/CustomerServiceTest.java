package com.endiluamba.creditmanager.customers.service;

import com.endiluamba.creditmanager.customers.builder.CustomerDTOBuilder;
import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.dto.MessageDTO;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.exception.CustomerAlreadyExistsException;
import com.endiluamba.creditmanager.customers.mapper.CustomerMapper;
import com.endiluamba.creditmanager.customers.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    private final static CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Mock
    private CustomerRepository customerRepository;

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
        String expectedCreationMessage = "Customer Endi Luamba with ID 1 successfully created";
        String expectedCustomerCpf = expectedCreatedCustomerDTO.getCpf();
        String expectedCustomerEmail = expectedCreatedCustomerDTO.getEmail();

        when(customerRepository.findByCpfOrEmail(expectedCustomerCpf, expectedCustomerEmail)).thenReturn(Optional.empty());
        when(customerRepository.save(expectedCreatedCustomer)).thenReturn(expectedCreatedCustomer);

        MessageDTO creationMessage = customerService.create(expectedCreatedCustomerDTO);

        assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
    }

    @Test
    void whenExistingCustomerIsInformedThenAnExceptionShouldBeThrown() {
        CustomerDTO expectedDuplicatedCustomerDTO = customerDTOBuilder.buildCustomerDTO();
        Customer expectedDuplicatedCustomer = customerMapper.toModel(expectedDuplicatedCustomerDTO);
        String expectedCustomerCpf = expectedDuplicatedCustomerDTO.getCpf();
        String expectedCustomerEmail = expectedDuplicatedCustomerDTO.getEmail();

        when(customerRepository.findByCpfOrEmail(expectedCustomerCpf, expectedCustomerEmail)).thenReturn(Optional.of(expectedDuplicatedCustomer));

        assertThrows(CustomerAlreadyExistsException.class, () -> customerService.create(expectedDuplicatedCustomerDTO));
    }


}
