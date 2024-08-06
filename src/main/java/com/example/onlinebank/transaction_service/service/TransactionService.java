package com.example.onlinebank.transaction_service.service;

import com.example.onlinebank.account_service.entity.Account;
import com.example.onlinebank.account_service.exception.AccountNotFoundException;
import com.example.onlinebank.account_service.repository.AccountRepository;
import com.example.onlinebank.notification_service.entity.Notification;
import com.example.onlinebank.notification_service.service.NotificationService;
import com.example.onlinebank.payment_service.entity.Payment;
import com.example.onlinebank.payment_service.entity.PaymentStatus;
import com.example.onlinebank.payment_service.exception.PaymentCreationException;
import com.example.onlinebank.payment_service.exception.PaymentNotException;
import com.example.onlinebank.payment_service.service.PaymentService;
import com.example.onlinebank.transaction_service.entity.Transaction;
import com.example.onlinebank.transaction_service.exception.TransactionNotFoundException;
import com.example.onlinebank.transaction_service.repository.TransactionRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    TransactionRepository transactionRepository;

    AccountRepository accountRepository;

    NotificationService notificationService;
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
        transaction.setTransactionDate(LocalDate.now());
        transaction.setAmount(amount);
        transaction.setDescription(description);

        notificationService.sendNotification(new Notification("Transaction successful", "Your transaction has been successfully", transaction.getAccount().getClient().getId()));

        return transactionRepository.save(transaction);
    }

    @SneakyThrows
    public Transaction createTransactionWithPayment(Long accountId, BigDecimal amount, String description) {
        Transaction transaction = createTransaction(accountId, amount, description);
        Payment payment = new Payment();
        payment.setTransaction(transaction);
        payment.setAmount(amount);
        payment.setPaymentStatus(PaymentStatus.NEW);
        try {
            paymentService.createPayment(payment);
        }catch (Exception e) {
            throw new PaymentCreationException("Payment creation failed");
        }
        return transaction;
    }

    public Transaction updateTransaction(Long transactionId, BigDecimal amount, String description) {
        Transaction transaction = getTransaction(transactionId);
        transaction.setAmount(amount);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    @SneakyThrows
    public void updateOrDeleteTransactionAndPayment(Long transactionId, PaymentStatus paymentStatus) {
        Transaction transaction = getTransaction(transactionId);
        if (transaction.getPayment() == null) {
            throw new PaymentNotException("Payment not found for transaction");
        }
        Payment payment = paymentService.getPayment(transaction.getPayment().getId());
        if (paymentStatus != null) {
            paymentService.updatePaymentStatus(payment.getId(), paymentStatus);
        } else {
            paymentService.deletePayment(payment.getId());
        }
        if (paymentStatus == null) {
            deleteTransaction(transactionId);
        }
    }

    public void deleteTransaction(Long transactionId) {
        Transaction transaction = getTransaction(transactionId);
        if (transaction == null) {
            throw new TransactionNotFoundException("Transaction not found");
        }
        transactionRepository.deleteById(transactionId);
    }
}
