package com.recargapay.wallet.models.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {

    private Long walletId;
    private BigDecimal amount;

}
