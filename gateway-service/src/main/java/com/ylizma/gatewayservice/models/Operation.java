package com.ylizma.gatewayservice.models;

import com.ylizma.gatewayservice.models.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Operation {

    private Long id;

    private Account account;

    private Date opDateTime;

    private String opType;

    private BigDecimal opAmount;
}
