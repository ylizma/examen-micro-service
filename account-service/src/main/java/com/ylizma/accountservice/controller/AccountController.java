package com.ylizma.accountservice.controller;


import com.ylizma.accountservice.models.Account;
import com.ylizma.accountservice.models.AccountCustomerInfo;
import com.ylizma.accountservice.models.Operation;
import com.ylizma.accountservice.models.OperationDetails;
import com.ylizma.accountservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public Account getByAccountNumber(@PathVariable Long accountId) {
        return accountService.findAccountById(accountId).get();
    }

    @PostMapping("/add")
    public Account addNewAccount(@RequestBody AccountCustomerInfo accountInformation) {
        return accountService.addNewAccount(accountInformation);
    }

    @GetMapping("/all")
    public List<Account> getAllAccounts(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "15") int size) {
        return accountService.findAllAccounts(page, size);
    }

    @GetMapping("/customer/{customerId}")
    public Account getAccountByCustomer(@PathVariable Long customerId) {
        return accountService.findAccountByCustomerId(customerId).get();
    }

    @PostMapping(path = "/virement")
    public ResponseEntity<Object> transferDetails(@RequestBody OperationDetails operationDetails) {
        return accountService.virementDetails(operationDetails);
    }

    @PostMapping(path = "/versement")
    public ResponseEntity<Object> versementDetails(@RequestBody OperationDetails operationDetails) {
        return accountService.versementDetails(operationDetails);
    }

    @PostMapping(path = "/retrait")
    public ResponseEntity<Object> retraitDetails(@RequestBody OperationDetails operationDetails) {
        return accountService.retraitDetails(operationDetails);
    }

    @GetMapping(path = "/operation/{accountId}")
    public List<Operation> getTransactionByAccountNumber(@PathVariable Long accountId) {
        return accountService.findOperationsByAccountNumber(accountId);
    }

    @GetMapping("/activate/{accountId}")
    public ResponseEntity<String> activateAccount(@PathVariable Long accountId) {
        return accountService.activateAccount(accountId);
    }

    @GetMapping("/suspend/{accountId}")
    public ResponseEntity<String> suspendAccount(@PathVariable Long accountId) {
        return accountService.suspendAccount(accountId);
    }
}
