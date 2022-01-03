package com.endiluamba.creditmanager.loans.entity;

import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.entity.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Loan extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double loanAmount;

    @Column(columnDefinition = "integer default 0")
    private int installments;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate firstInstallmentDate;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Customer customer;
}
