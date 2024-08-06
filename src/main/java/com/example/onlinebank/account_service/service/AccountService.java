package com.example.onlinebank.account_service.service;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.account_service.repository.AccountRepository;
import com.example.onlinebank.notification_service.entity.Notification;
import com.example.onlinebank.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    private NotificationService notificationService;

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public Account createAccount(Account account) {
        notificationService.sendNotification(new Notification("Account created", "Your account has been created successfully",
                account.getClient().getId()));
        return accountRepository.save(account);
    }

    public Account updateAccount(Long accountId, Account account) {
        Account existingAccount = getAccountById(accountId);
        existingAccount.setBalance(account.getBalance());
        return accountRepository.save(existingAccount);
    }

    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    public List<Account> getAccountsByAccountHolderName(String accountHolderName) {
        return accountRepository.findByAccountHolderName(accountHolderName);
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
