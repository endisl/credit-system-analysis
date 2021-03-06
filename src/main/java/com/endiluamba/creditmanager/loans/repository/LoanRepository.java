package com.endiluamba.creditmanager.loans.repository;

import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByLoanAmountAndInstallmentsAndFirstInstallmentDateAndCustomer
            (Double loanAmount, Integer installments, LocalDate localDate, Customer customer);

    Optional<Loan> findByIdAndCustomer(Long id, Customer customer);

    List<Loan> findAllByCustomer(Customer customer);

    void deleteByIdAndCustomer(Long id, Customer customer);
}
