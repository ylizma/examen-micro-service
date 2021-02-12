package com.ylizma.gatewayservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Customer {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;
}
