package com.example.kuit_server.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HoldingDollarInfo {
    private int userId;
    private double holdingDollar;
}
