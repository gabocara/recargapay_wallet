package com.recargapay.wallet.controllers;

import com.recargapay.wallet.models.dto.CreateWalletRequest;
import com.recargapay.wallet.models.dto.CreateWalletResponse;
import com.recargapay.wallet.models.dto.TransactionRequest;
import com.recargapay.wallet.models.dto.TransferRequest;
import com.recargapay.wallet.models.dto.WalletDto;
import com.recargapay.wallet.services.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/create")
    public ResponseEntity<CreateWalletResponse> createWallet(@RequestBody CreateWalletRequest request) {
        return ResponseEntity.ok(walletService.createWallet(request.getUserId()));
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long walletId) {
        BigDecimal balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{walletId}/balance/historical")
    public ResponseEntity<BigDecimal> getHistoricalBalance(@PathVariable Long walletId, @RequestParam String timestamp) {
        LocalDateTime time = LocalDateTime.parse(timestamp);
        BigDecimal balance = walletService.getHistoricalBalance(walletId, time);
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/deposit")
    public ResponseEntity<WalletDto> deposit(@RequestBody TransactionRequest request) {
        WalletDto walletDto = walletService.deposit(request.getWalletId(), request.getAmount());
        return ResponseEntity.ok(walletDto);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WalletDto> withdraw(@RequestBody TransactionRequest request) {
        WalletDto walletDto = walletService.withdraw(request.getWalletId(), request.getAmount());
        return ResponseEntity.ok(walletDto);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest request) {
        walletService.transfer(request.getFromWalletId(), request.getToWalletId(), request.getAmount());
        return ResponseEntity.ok("Transfer completed");
    }
}
