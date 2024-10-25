package com.recargapay.wallet.models.mapper;

import com.recargapay.wallet.models.dto.WalletResponse;
import com.recargapay.wallet.models.entity.Wallet;
import com.recargapay.wallet.models.dto.CreateWalletResponse;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public static CreateWalletResponse toDto(Wallet wallet) {
        CreateWalletResponse response = new CreateWalletResponse();
        response.setWalletId(wallet.getId());
        response.setUserId(wallet.getUser().getId());
        return response;
    }

    public WalletResponse toWalletResponse(Wallet wallet) {
        WalletResponse response = new WalletResponse();
        response.setId(wallet.getId());
        response.setBalance(wallet.getBalance());
        response.setLastUpdated(wallet.getLastUpdated());
        return response;
    }
}
