package com.endiluamba.creditmanager.customers.entity;

import com.endiluamba.creditmanager.customers.enums.Role;
import com.endiluamba.creditmanager.entity.Auditable;
import com.endiluamba.creditmanager.loans.entity.Loan;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;
}
