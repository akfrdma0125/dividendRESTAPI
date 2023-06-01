package com.example.kuit_server.controller;

import com.example.kuit_server.common.exception.StockException;
import com.example.kuit_server.common.response.BaseResponse;
import com.example.kuit_server.dto.stock.GetStockRes;
import com.example.kuit_server.mapper.StockMapper;
import com.example.kuit_server.service.StockService;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.INVALID_STOCK_VALUE;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {
    private final StockService StockService;

    @GetMapping("")
    public BaseResponse<GetStockRes> getStock(@RequestParam @Nullable String stockName, @RequestParam @Nullable String stockId){
        log.info("[StockController.getStock] stockName = {}, stockId = {}",stockName,stockId);

        if(stockName!=null) {
            return new BaseResponse<>(StockService.getStockByName(stockName));
        }
        if(stockId!=null){
            int id = Integer.parseInt(stockId);
            return new BaseResponse<>(StockService.getStockById(id));
        }
        throw new StockException(INVALID_STOCK_VALUE);
    }
}
