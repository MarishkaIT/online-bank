package com.example.onlinebank.account_service.repository;

import com.example.onlinebank.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByAccountHolderName(String accountHolderName);

    Account findByAccountNumber(String accountNumber);
}
