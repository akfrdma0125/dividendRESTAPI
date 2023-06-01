package com.example.kuit_server.dto.holding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class GetHoldingRes {
    private int holdingId;
    private int userId;
    private int stockId;
    private String stockName;
    private double price;
    private double exchangeRate;
    private int quantity;
    private Timestamp createdAt;
    private Timestamp deletedAt;
}
