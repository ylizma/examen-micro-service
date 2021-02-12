package com.ylizma.gatewayservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Account {

    private Long id;

    private Long accountNumber;

    private String accountStatus;

    private BigDecimal accountBalance;

    private Customer customer;

    private Long customerId;

    private Date createDateTime;
}
