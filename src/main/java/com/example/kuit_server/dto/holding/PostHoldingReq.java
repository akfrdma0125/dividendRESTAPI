package com.example.kuit_server.dto.holding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;


@Getter
public class PostHoldingReq {
    @Range(min=1,message = "userId: 아이디는 {min} 이상부터 가능합니다")
    private int userId;

    @Range(min=1,message = "stockId: 아이디는 {min} 이상부터 가능합니다")
    private int stockId;

    @NotBlank(message = "stockName: {NotBlank}")
    @Length(max = 50, message = "stockName: 최대 {max}자리까지 가능합니다")
    private String stockName;

    @Range(min=0,message = "price: {min} 이상부터 가능합니다")
    private double price;

    @Range(min=0,message = "exchangeRate: {min} 이상부터 가능합니다")
    private double exchangeRate;

    @Range(min=1,message = "quantity: {min} 이상부터 가능합니다")
    private int quantity;
}
