package com.techtask.controller;

import com.techtask.dto.WalletUpdateRequest;
import com.techtask.exception.InsufficientFundsException;
import com.techtask.exception.InvalidJsonException;
import com.techtask.exception.WalletNotFoundException;
import com.techtask.model.Wallet;
import com.techtask.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<String> updateWalletBalance(@RequestBody WalletUpdateRequest request) {
        try {
            walletService.updateWalletBalance(request.getWalletId(), request.getOperationType(), request.getAmount());
            return ResponseEntity.ok("Wallet balance updated successfully");
        } catch (InsufficientFundsException | InvalidJsonException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<?> getWallet(@PathVariable UUID walletId) {
        try {
            Wallet wallet = walletService.getWallet(walletId);
            return ResponseEntity.ok(wallet);
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}