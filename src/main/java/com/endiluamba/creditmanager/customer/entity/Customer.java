package com.endiluamba.creditmanager.customer.entity;

import com.endiluamba.creditmanager.loans.entity.Loan;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String rg;

    @Column(nullable = false)
    private Double income;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY) //EAGER
    private List<Loan> loans;


}
