package com.recargapay.wallet.services;

import com.recargapay.wallet.models.dto.CreateWalletResponse;
import com.recargapay.wallet.models.dto.WalletDto;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface WalletService {

    @Transactional
    CreateWalletResponse createWallet(Long userId);

    BigDecimal getBalance(Long walletId);
    BigDecimal getHistoricalBalance(Long walletId, LocalDateTime timestamp);
    WalletDto deposit(Long walletId, BigDecimal amount);
    WalletDto withdraw(Long walletId, BigDecimal amount);
    void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount);

}
