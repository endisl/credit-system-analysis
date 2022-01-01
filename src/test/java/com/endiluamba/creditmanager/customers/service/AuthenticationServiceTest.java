package com.endiluamba.creditmanager.customers.service;

import com.endiluamba.creditmanager.customers.builder.CustomerDTOBuilder;
import com.endiluamba.creditmanager.customers.dto.CustomerDTO;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.mapper.CustomerMapper;
import com.endiluamba.creditmanager.customers.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    private final static CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    private CustomerDTOBuilder customerDTOBuilder;

    @BeforeEach
    void setUp() {
        customerDTOBuilder = CustomerDTOBuilder.builder().build();
    }

    @Test
    void whenEmailIsInformedThenCustomerShouldBeReturned() {
        CustomerDTO expectedFoundCustomerDTO = customerDTOBuilder.buildCustomerDTO();
        Customer expectedFoundCustomer = customerMapper.toModel(expectedFoundCustomerDTO);
        SimpleGrantedAuthority expectedCustomerRole = new SimpleGrantedAuthority("ROLE_" + expectedFoundCustomerDTO.getRole().getDescription());
        String expectedEmail = expectedFoundCustomerDTO.getEmail();

        when(customerRepository.findByEmail(expectedEmail)).thenReturn(Optional.of(expectedFoundCustomer));

        UserDetails userDetails = authenticationService.loadUserByUsername(expectedEmail);

        assertThat(userDetails.getUsername(), is(equalTo(expectedFoundCustomer.getEmail())));
        assertThat(userDetails.getPassword(), is(equalTo(expectedFoundCustomer.getPassword())));
        assertTrue(userDetails.getAuthorities().contains(expectedCustomerRole));
    }

    @Test
    void whenInvalidEmailIsInformedThenAnExceptionShouldBeThrown() {
        CustomerDTO expectedFoundCustomerDTO = customerDTOBuilder.buildCustomerDTO();
        String expectedEmail = expectedFoundCustomerDTO.getEmail();

        when(customerRepository.findByEmail(expectedEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(expectedEmail));
    }
}
