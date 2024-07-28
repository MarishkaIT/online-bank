package com.example.onlinebank.account_service.controller;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.account_service.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("api/controller")
public class AccountController {

    @Autowired
    private AccountService accountService;
    private RestTemplate restTemplate;

    @GetMapping
    public List<Account> getAllAccounts(){
        String url = "http://" + "client-service" + "/api/clients";
        return restTemplate.getForObject(url, List.class);
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable("id") Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping
    public Account createAccount(@Valid @RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @PutMapping ("/{accountId}")
    public Account updateAccount(@PathVariable("accountId") Long accountId, @RequestBody Account account) {
        return accountService.updateAccount(accountId, account);
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.deleteAccount(accountId);
    }

    @GetMapping("/by-account-holderName/{accountHolderName}")
    public List<Account> getAccountsByAccountHolderName(@PathVariable("accountHolderName") String accountHolderName) {
        return accountService.getAccountsByAccountHolderName(accountHolderName);
    }

    @GetMapping("/by-accountNumber/{accountNumber}")
    public Account getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        return accountService.getAccountByAccountNumber(accountNumber);
    }
}
