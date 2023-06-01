package com.example.kuit_server.dto.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class GetStockRes {
    private int stockId;
    private String stockName;
    private double actualDividend;
    private Timestamp updatedAt;
}
