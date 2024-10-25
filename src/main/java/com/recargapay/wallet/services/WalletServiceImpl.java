package com.recargapay.wallet.services;

import com.recargapay.wallet.exception.ResourceNotFoundException;
import com.recargapay.wallet.models.entity.Transaction;
import com.recargapay.wallet.models.entity.TransactionType;
import com.recargapay.wallet.models.entity.User;
import com.recargapay.wallet.models.entity.Wallet;
import com.recargapay.wallet.models.dto.CreateWalletResponse;
import com.recargapay.wallet.models.dto.WalletDto;
import com.recargapay.wallet.repository.TransactionRepository;
import com.recargapay.wallet.repository.UserRepository;
import com.recargapay.wallet.repository.WalletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @Transactional
    public CreateWalletResponse createWallet(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setLastUpdated(LocalDateTime.now());

        Wallet savedWallet = walletRepository.save(wallet);

        return objectMapper.convertValue(savedWallet, CreateWalletResponse.class);
    }

    @Override
    @Cacheable(value = "walletBalance", key = "#walletId")
    public BigDecimal getBalance(Long walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));
        return wallet.getBalance();
    }

    @Override
    public BigDecimal getHistoricalBalance(Long walletId, LocalDateTime timestamp) {
        List<Transaction> transactions = transactionRepository.findByWalletIdAndTimestampBefore(walletId, timestamp);
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    @CacheEvict(value = "walletBalance", key = "#walletId")
    public WalletDto deposit(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        wallet.setBalance(wallet.getBalance().add(amount));
        wallet.setLastUpdated(LocalDateTime.now());

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(TransactionType.DEPOSIT);
        transactionRepository.save(transaction);

        Wallet savedWallet = walletRepository.save(wallet);

        return objectMapper.convertValue(savedWallet, WalletDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "walletBalance", key = "#walletId")
    public WalletDto withdraw(Long walletId, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        wallet.setLastUpdated(LocalDateTime.now());

        Transaction transaction = new Transaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount.negate());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setType(TransactionType.WITHDRAW);
        transactionRepository.save(transaction);

        Wallet savedWallet = walletRepository.save(wallet);

        return objectMapper.convertValue(savedWallet, WalletDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "walletBalance", allEntries = true)
    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        Wallet fromWallet = walletRepository.findById(fromWalletId)
                .orElseThrow(() -> new ResourceNotFoundException("Source wallet not found"));
        Wallet toWallet = walletRepository.findById(toWalletId)
                .orElseThrow(() -> new ResourceNotFoundException("Target wallet not found"));

        if (fromWallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance in source wallet");
        }

        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));

        fromWallet.setLastUpdated(LocalDateTime.now());
        toWallet.setLastUpdated(LocalDateTime.now());

        Transaction withdrawal = new Transaction();
        withdrawal.setWallet(fromWallet);
        withdrawal.setAmount(amount.negate());
        withdrawal.setTimestamp(LocalDateTime.now());
        withdrawal.setType(TransactionType.TRANSFER);
        transactionRepository.save(withdrawal);

        Transaction deposit = new Transaction();
        deposit.setWallet(toWallet);
        deposit.setAmount(amount);
        deposit.setTimestamp(LocalDateTime.now());
        deposit.setType(TransactionType.TRANSFER);
        transactionRepository.save(deposit);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);
    }

}
