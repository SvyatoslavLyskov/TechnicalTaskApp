package com.techtask.service;

import com.techtask.exception.InsufficientFundsException;
import com.techtask.exception.InvalidOperationException;
import com.techtask.exception.WalletNotFoundException;
import com.techtask.model.OperationType;
import com.techtask.model.Wallet;
import com.techtask.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    public void updateWalletBalance(UUID walletId, OperationType operationType, double amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        if (operationType == OperationType.DEPOSIT) {
            wallet.deposit(amount);
        } else if (operationType == OperationType.WITHDRAW) {
            if (wallet.getBalance() < amount) {
                throw new InsufficientFundsException("Insufficient funds in the wallet");
            }
            wallet.withdraw(amount);
        } else {
            throw new InvalidOperationException("Invalid operation type");
        }

        walletRepository.save(wallet);
    }

    public Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
    }
}