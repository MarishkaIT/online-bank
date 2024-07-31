package com.example.onlinebank.transaction_service.repository;

import com.example.onlinebank.transaction_service.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);
}
