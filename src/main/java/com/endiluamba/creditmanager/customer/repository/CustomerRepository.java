package com.endiluamba.creditmanager.customer.repository;

import com.endiluamba.creditmanager.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
