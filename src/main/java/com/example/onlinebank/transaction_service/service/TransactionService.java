package com.example.onlinebank.transaction_service.service;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.account_service.exception.AccountNotFoundException;
import com.example.onlinebank.account_service.repository.AccountRepository;
import com.example.onlinebank.payment_service.entity.Payment;
import com.example.onlinebank.payment_service.service.PaymentService;
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

    PaymentService paymentService;

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

    public Transaction createTransactionWithPayment(Long accountId, BigDecimal amount, String description) {
        Transaction transaction = createTransaction(accountId, amount, description);
        Payment payment = new Payment();
        payment.setTransaction(transaction);
        payment.setAmount(amount);
        payment.setPaymentStatus("NEW");
        paymentService.createPayment(payment);
        return transaction;
    }

    public Transaction updateTransaction(Long transactionId, BigDecimal amount, String description) {
        Transaction transaction = getTransaction(transactionId);
        transaction.setAmount(amount);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    public void updateTransactionAndPaymentStatus(Long transactionId, String paymentStatus) {
        Transaction transaction = getTransaction(transactionId);
        Payment payment = paymentService.getPayment(transaction.getPayment().getId());
        paymentService.updatePaymentStatus(payment.getId(), paymentStatus);
    }

    public void deleteTransactionAndPayment(Long transactionId) {
        Transaction transaction = getTransaction(transactionId);
        paymentService.deletePayment(transaction.getPayment().getId());
        deleteTransaction(transactionId);
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }
}
