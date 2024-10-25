package com.recargapay.wallet.models.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WalletDto {

    private Long id;
    private Long userId;
    private BigDecimal balance;
    private LocalDateTime lastUpdated;

}
