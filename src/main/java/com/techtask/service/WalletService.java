package com.techtask.service;

import com.techtask.exception.InsufficientFundsException;
import com.techtask.exception.InvalidOperationException;
import com.techtask.exception.WalletNotFoundException;
import com.techtask.model.OperationType;
import com.techtask.model.Wallet;
import com.techtask.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    @Async("threadPoolTaskExecutor")
    @Transactional(rollbackFor = {InsufficientFundsException.class, InvalidOperationException.class, WalletNotFoundException.class},
            isolation = Isolation.READ_COMMITTED)
    public void updateWalletBalance(UUID walletId, OperationType operationType, double amount) {
        Wallet wallet = getWallet(walletId);

        switch (operationType) {
            case DEPOSIT:
                wallet.deposit(amount);
                break;
            case WITHDRAW:
                if (wallet.getBalance() < amount) {
                    throw new InsufficientFundsException("Insufficient funds in the wallet");
                }
                wallet.withdraw(amount);
                break;
            default:
                throw new InvalidOperationException("Invalid operation type");
        }

        try {
            walletRepository.save(wallet);
        } catch (DataAccessException ex) {
            throw new InvalidOperationException("Error saving wallet changes");
        }
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
    }
}
