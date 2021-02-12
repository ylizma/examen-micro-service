package com.ylizma.kafkaoperationsanalytic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class Operation {

    private Long id;

    private Long account;

    private Date opDateTime;

    //operation Type
    private String opType;

    //operation Amount
    private BigDecimal opAmount;
}
