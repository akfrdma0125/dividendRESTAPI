package com.example.kuit_server.dto.dividend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class GetDividendRes {
    private int holdingId;
    private int stockId;
    private double dividend;
    private int quantity;
    private Timestamp createdAt;
}
