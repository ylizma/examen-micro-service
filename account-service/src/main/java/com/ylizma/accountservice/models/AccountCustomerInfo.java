package com.ylizma.accountservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCustomerInfo {

    private Long accountNumber;

    private String accountStatus;

    private BigDecimal accountBalance;

    private Long customerId;
}
