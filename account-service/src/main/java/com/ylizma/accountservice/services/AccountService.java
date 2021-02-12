package com.ylizma.accountservice.services;

import com.ylizma.accountservice.models.*;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    PagedModel<Customer> findAllCustomers();

    Customer findByCustomerId(Long customerNumber);

    Optional<Account> findAccountById(Long accountId);

    Account addNewAccount(AccountCustomerInfo accountInformation);

    List<Account> findAllAccounts(int page, int size);

    Optional<Account> findAccountByCustomerId(Long customerId);

    ResponseEntity<Object> virementDetails(OperationDetails operationDetails);

    ResponseEntity<Object> versementDetails(OperationDetails operationDetails);

    ResponseEntity<Object> retraitDetails(OperationDetails operationDetails);

    List<Operation> findOperationsByAccountNumber(Long accountId);

    ResponseEntity<String> activateAccount(Long accountId);

    ResponseEntity<String> suspendAccount(Long accountId);
}
