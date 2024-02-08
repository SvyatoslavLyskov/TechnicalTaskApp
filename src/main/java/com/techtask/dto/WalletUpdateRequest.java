package com.techtask.dto;

import com.techtask.model.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletUpdateRequest {
    private UUID walletId;
    private OperationType operationType;
    private double amount;
}