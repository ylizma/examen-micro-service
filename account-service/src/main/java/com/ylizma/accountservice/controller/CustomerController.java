package com.ylizma.accountservice.controller;

import com.ylizma.accountservice.models.Customer;
import com.ylizma.accountservice.openfeign.CustomerRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRestClient customerRestClient;

    @GetMapping
    public Collection<Customer> getAllCustomers(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size) {
        return customerRestClient.getAllCustomers(page, size).getContent();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId){
        return customerRestClient.findCustomerById(customerId);
    }
}
