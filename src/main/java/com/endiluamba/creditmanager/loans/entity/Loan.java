package com.endiluamba.creditmanager.loans.entity;

import com.endiluamba.creditmanager.customers.entity.Customer;
import com.endiluamba.creditmanager.entity.Auditable;
import com.endiluamba.creditmanager.loans.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "loans")
public class Loan extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double loanAmount;

    @Column(nullable = false)
    private int installments;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate firstInstallmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Customer customer;
}
