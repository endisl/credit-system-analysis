package com.endiluamba.creditmanager.customers.repository;

import com.endiluamba.creditmanager.customers.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCpfOrEmail(String cpf, String email);
}
