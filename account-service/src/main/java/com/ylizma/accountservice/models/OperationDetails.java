package com.ylizma.accountservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperationDetails {

	private Optional<Long> fromAccountNumber;
	
	private Optional<Long>  toAccountNumber;
	
	private BigDecimal transferAmount;
}
