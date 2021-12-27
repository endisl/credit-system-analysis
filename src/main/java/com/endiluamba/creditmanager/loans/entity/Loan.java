package com.endiluamba.creditmanager.loans.entity;

import com.endiluamba.creditmanager.customer.entity.Customer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double loanAmount;

    @Column(columnDefinition = "integer default 0")
    private int installments;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDate firstInstallmentDate;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Customer customer;


}
