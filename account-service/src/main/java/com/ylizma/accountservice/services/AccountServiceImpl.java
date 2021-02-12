package com.ylizma.accountservice.services;

import com.ylizma.accountservice.models.*;
import com.ylizma.accountservice.openfeign.CustomerRestClient;
import com.ylizma.accountservice.repositories.AccountRepository;
import com.ylizma.accountservice.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private CustomerRestClient customerRestClient;
    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    @Autowired
    private OperationProducer operationProducer;

    public AccountServiceImpl(CustomerRestClient customerRestClient, AccountRepository accountRepository, OperationRepository operationRepository) {
        this.customerRestClient = customerRestClient;
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public PagedModel<Customer> findAllCustomers() {
        return customerRestClient.getAllCustomers(0, 15);
    }

    @Override
    public Customer findByCustomerId(Long customerId) {
        return customerRestClient.findCustomerById(customerId);
    }

    @Override
    public Optional<Account> findAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Account addNewAccount(AccountCustomerInfo accountInformation) {
        Customer customer = findByCustomerId(accountInformation.getCustomerId());
        Account account = new Account(null, accountInformation.getAccountNumber(), AccountState.ACTIVATED
                , accountInformation.getAccountBalance()
                , customer, customer.getId(), new Date());
        return accountRepository.save(account);
    }

    public List<Account> findAllAccounts(int page, int size) {
        List<Account> accounts = accountRepository.findAll(PageRequest.of(page, size)).getContent();
        accounts.forEach(account -> {
            account.setCustomer(customerRestClient.findCustomerById(account.getCustomerId()));
        });
        return accounts;
    }

    @Override
    public Optional<Account> findAccountByCustomerId(Long customerId) {
        Optional<Account> account = accountRepository.findAccountByCustomerId(customerId);
        account.get().setCustomer(customerRestClient.findCustomerById(customerId));
        return account;
    }

    @Override
    public ResponseEntity<Object> virementDetails(OperationDetails operationDetails) {
        List<Account> accountEntities = new ArrayList<>();
        Account fromAccountEntity = null;
        Account toAccountEntity = null;

        Optional<Account> fromAccountEntityOpt = accountRepository.findById(operationDetails.getFromAccountNumber().get());
        if (fromAccountEntityOpt.isPresent()) {
            if (fromAccountEntityOpt.get().getAccountStatus().equals(AccountState.ACTIVATED)) {
                fromAccountEntity = fromAccountEntityOpt.get();
            } else {
                return ResponseEntity.status(500).body("the account is suspended");
            }
        } else {
            // if from request does not exist, 404 Bad Request
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("From Account Number " + operationDetails
                    .getFromAccountNumber() + " not found.");
        }

        // get TO ACCOUNT info
        Optional<Account> toAccountEntityOpt = accountRepository.findById(operationDetails.getToAccountNumber().get());
        if (toAccountEntityOpt.isPresent()) {
            if (fromAccountEntityOpt.get().getAccountStatus().equals(AccountState.ACTIVATED)) {
                toAccountEntity = toAccountEntityOpt.get();
            } else {
                return ResponseEntity.status(500).body("the account is suspended");
            }
        } else {
            // if from request does not exist, 404 Bad Request
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("To Account Number " + operationDetails.getToAccountNumber() + " not found.");
        }

        // if not sufficient funds, return 400 Bad Request
        if (fromAccountEntity.getAccountBalance().compareTo(operationDetails.getTransferAmount()) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient Funds.");
        } else {
            synchronized (this) {
                // update FROM ACCOUNT
                fromAccountEntity.setAccountBalance(fromAccountEntity.getAccountBalance().subtract(operationDetails.getTransferAmount()));
                accountEntities.add(fromAccountEntity);

                // update TO ACCOUNT
                toAccountEntity.setAccountBalance(toAccountEntity.getAccountBalance().add(operationDetails.getTransferAmount()));
                accountEntities.add(toAccountEntity);

                accountRepository.saveAll(accountEntities);

                // Create transaction for FROM Account
                Operation fromTransaction = new Operation(null, fromAccountEntity, new Date(), OperationType.VIREMENT, operationDetails.getTransferAmount());
                operationRepository.save(fromTransaction);
                operationProducer.operationSupplier(fromTransaction);

                // Create transaction for TO Account
                Operation toTransaction = new Operation(null, toAccountEntity, new Date(), OperationType.VIREMENT, operationDetails.getTransferAmount());
                operationRepository.save(toTransaction);
                operationProducer.operationSupplier(toTransaction);
            }
            return ResponseEntity.status(HttpStatus.OK).body("Success: Amount transferred for account Number "
                    + operationDetails.getToAccountNumber());
        }
    }

    @Override
    public ResponseEntity<Object> versementDetails(OperationDetails operationDetails) {
        Optional<Account> account = accountRepository.findById(operationDetails.getToAccountNumber().get());
        if (account.isPresent() && account.get().getAccountStatus().equals(AccountState.ACTIVATED)) {
            account.get().setAccountBalance(account.get().getAccountBalance().add(operationDetails.getTransferAmount()));
            accountRepository.save(account.get());
            Operation operation = new Operation(null, account.get(), new Date(), OperationType.VERSEMENT, operationDetails.getTransferAmount());
            operationRepository.save(operation);
            return ResponseEntity.ok("the operation is successfuly done");
        } else {
            return ResponseEntity.status(400).body("account not found or suspended!!");
        }
    }

    @Override
    public ResponseEntity<Object> retraitDetails(OperationDetails operationDetails) {
        Optional<Account> account = accountRepository.findById(operationDetails.getFromAccountNumber().get());
        if (account.isPresent() && account.get().getAccountStatus().equals(AccountState.ACTIVATED)) {
            if (operationDetails.getTransferAmount().compareTo(account.get().getAccountBalance()) > 0) {
                return ResponseEntity.status(400).body("no enough balance !!!");
            }
            account.get().setAccountBalance(account.get().getAccountBalance().subtract(operationDetails.getTransferAmount()));
            accountRepository.save(account.get());
            Operation operation = new Operation(null, account.get(), new Date(), OperationType.RETRAIT, operationDetails.getTransferAmount());
            operationRepository.save(operation);
            return ResponseEntity.ok("the operation is successfuly done");
        } else {
            return ResponseEntity.status(400).body("account not found or suspended!!");
        }
    }

    @Override
    public List<Operation> findOperationsByAccountNumber(Long accountId) {
        System.out.println(accountId);
        List<Operation> operations = operationRepository.findOperationsByAccountId(accountId);
        operations.forEach(operation -> {
            operation.getAccount().setCustomer(customerRestClient.findCustomerById(operation.getAccount().getCustomerId()));
        });
        return operations;
    }

    @Override
    public ResponseEntity<String> activateAccount(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            account.get().setAccountStatus(AccountState.ACTIVATED);
            return ResponseEntity.ok("the account is activated");
        } else {
            return ResponseEntity.status(400).body("account not found");
        }
    }

    @Override
    public ResponseEntity<String> suspendAccount(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()) {
            account.get().setAccountStatus(AccountState.SUSPENDED);
            return ResponseEntity.ok("the account is suspended");
        } else {
            return ResponseEntity.status(400).body("account not found");
        }
    }

}
