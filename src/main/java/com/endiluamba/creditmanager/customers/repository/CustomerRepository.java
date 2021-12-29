package com.endiluamba.creditmanager.customers.repository;

import com.endiluamba.creditmanager.customers.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
