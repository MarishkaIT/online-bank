package com.example.onlinebank.transaction_service.controller;

import com.example.onlinebank.transaction_service.entity.Transaction;
import com.example.onlinebank.transaction_service.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransaction() {
        return transactionService.getAllTransaction();
    }

    @GetMapping("/{transactionId}")
    public Transaction getTransaction(@PathVariable Long transactionId) {
        return transactionService.getTransaction(transactionId);
    }

    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionByAccountId(@PathVariable Long accountId) {
        return transactionService.getTransactionByAccountId(accountId);
    }

    @GetMapping("/date-range")
    public List<Transaction> getTransactionsByDateRange(@RequestBody Date startDate, @RequestBody Date endDate) {
        return transactionService.getTransactionsByDateRange(startDate, endDate);
    }

    @PostMapping
    public Transaction createTransaction(@RequestParam Long accountId, @RequestParam BigDecimal amount,
                                         @RequestParam String description) {
        return transactionService.createTransaction(accountId, amount, description);
    }

    @PutMapping("/{transactionId}")
    public Transaction updateTransaction(@PathVariable Long transactionId, @RequestParam BigDecimal amount,
                                         @RequestParam String description) {
        return transactionService.updateTransaction(transactionId, amount, description);
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }
}
