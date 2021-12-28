package com.endiluamba.creditmanager.loans.repository;

import com.endiluamba.creditmanager.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
