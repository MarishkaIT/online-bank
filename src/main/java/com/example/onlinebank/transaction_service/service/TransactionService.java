package com.example.onlinebank.transaction_service.service;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.account_service.exception.AccountNotFoundException;
import com.example.onlinebank.account_service.repository.AccountRepository;
import com.example.onlinebank.transaction_service.entity.Transaction;
import com.example.onlinebank.transaction_service.exception.TransactionNotFoundException;
import com.example.onlinebank.transaction_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    TransactionRepository transactionRepository;

    AccountRepository accountRepository;

    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    public Transaction getTransaction(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
    }
    public List<Transaction> getTransactionByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }

    public Transaction createTransaction(Long accountId, BigDecimal amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setTransactionDate(new Date());
        transaction.setAmount(amount);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long transactionId, BigDecimal amount, String description) {
        Transaction transaction = getTransaction(transactionId);
        transaction.setAmount(amount);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
