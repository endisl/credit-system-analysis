package com.endiluamba.creditmanager.customers.service;

import com.endiluamba.creditmanager.customers.dto.AuthenticatedUser;
import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.customers.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found", email)));
        return new AuthenticatedUser(
                customer.getEmail(),
                customer.getPassword(),
                customer.getRole().getDescription()
        );
    }
}
