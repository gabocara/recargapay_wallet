package com.recargapay.wallet.repository;

import com.recargapay.wallet.models.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletIdAndTimestampBefore(Long walletId, LocalDateTime timestamp);

}