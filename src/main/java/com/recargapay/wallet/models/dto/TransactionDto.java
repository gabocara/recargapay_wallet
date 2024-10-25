package com.recargapay.wallet.models.dto;

import com.recargapay.wallet.models.entity.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {

    private Long id;
    private Long walletId;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private TransactionType type;

}
