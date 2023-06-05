package com.example.kuit_server.dto.dividend;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Getter
@ToString
public class PostDividendReq {
    @Range(min=1,message = "userId: 아이디는 {min} 이상부터 가능합니다")
    private int userId;

    @Range(min=1,message = "stockId: 아이디는 {min} 이상부터 가능합니다")
    private int stockId;
    @Nullable
    private String stockName;

    @Range(min=1,message = "holdingId: 아이디는 {min} 이상부터 가능합니다")
    private int holdingId;

    @Range(min=1,message = "quantity: {min} 이상부터 가능합니다")
    private int quantity;

    @Nullable
    private double dividend = 0;


    public void setDividend(double dividend){
        this.dividend = dividend;
    }
    public void setStockName(String stockName){this.stockName = stockName;}
}
