package com.techtask.service;

import com.techtask.exception.InsufficientFundsException;
import com.techtask.exception.InvalidOperationException;
import com.techtask.exception.WalletNotFoundException;
import com.techtask.model.OperationType;
import com.techtask.model.Wallet;
import com.techtask.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void updateWalletBalance(UUID walletId, OperationType operationType, double amount) {
        writeLock.lock();
        try {
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
        } finally {
            writeLock.unlock();
        }
    }

    public Wallet getWallet(UUID walletId) {
        readLock.lock();
        try {
            return walletRepository.findById(walletId)
                    .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
        } finally {
            readLock.unlock();
        }
    }
}