package com.ylizma.accountservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account account;

    @Temporal(TemporalType.TIME)
    private Date opDateTime;

    //operation Type
    @Enumerated(EnumType.STRING)
    private OperationType opType;

    //operation Amount
    private BigDecimal opAmount;
}
