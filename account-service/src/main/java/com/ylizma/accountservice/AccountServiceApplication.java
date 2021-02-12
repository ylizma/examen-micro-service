package com.ylizma.accountservice;

import com.ylizma.accountservice.models.Account;
import com.ylizma.accountservice.models.AccountState;
import com.ylizma.accountservice.models.Customer;
import com.ylizma.accountservice.openfeign.CustomerRestClient;
import com.ylizma.accountservice.repositories.AccountRepository;
import com.ylizma.accountservice.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(AccountRepository accountService, CustomerRestClient customerRestClient) {
//        repositoryRestConfiguration.exposeIdsFor(Customer.class);
        return args -> {
            Customer c1 = customerRestClient.findCustomerById(1L);
            Customer c2 = customerRestClient.findCustomerById(2L);
            Account account = new Account(null, 122L, AccountState.ACTIVATED, new BigDecimal(2000), c1, c1.getId(), new Date());
            Account account2 = new Account(null, 12221L, AccountState.ACTIVATED, new BigDecimal(100), c1, c1.getId(), new Date());
            accountService.save(account);
            accountService.save(account2);
        };
    }
}
