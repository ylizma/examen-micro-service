package com.ylizma.accountservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountState accountStatus;

    private BigDecimal accountBalance;

    @Transient
    private Customer customer;

    private Long customerId;

    @Temporal(TemporalType.TIME)
    private Date createDateTime;
}
