package com.example.kuit_server.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StockInfo {
    private double forwardDividend;
    private String stockName;
}
