package com.example.kuit_server.controller;

import com.example.kuit_server.common.exception.HoldingException;
import com.example.kuit_server.common.response.BaseResponse;
import com.example.kuit_server.dto.holding.GetHoldingRes;
import com.example.kuit_server.dto.holding.PostHoldingReq;
import com.example.kuit_server.service.HoldingService;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.INVALID_HOLDING_VALUE;
import static com.example.kuit_server.utils.BindingResultUtils.getErrorMessages;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/holdings")
public class HoldingController {
    private final HoldingService holdingService;

    @PostMapping("")
    public BaseResponse createHoldingStock(@Validated @RequestBody PostHoldingReq postHoldingReq, BindingResult bindingResult){
        log.info("[HoldingController.createHoldingStock]");
        if(bindingResult.hasErrors()){
            throw new HoldingException(INVALID_HOLDING_VALUE, getErrorMessages(bindingResult));
        }
        holdingService.createHoldingStock(postHoldingReq);
        return new BaseResponse(null);
    }

    @PatchMapping("/{userId}")
    public BaseResponse deleteHoldingStocks(@PathVariable int userId, @RequestParam @Nullable String holdingId){
        log.info("[HoldingController.deleteHoldingStocks]");
        if(holdingId == null)
            throw new HoldingException(INVALID_HOLDING_VALUE,"보유 주식 아이디가 포함되지 않았습니다.");
        int id = Integer.parseInt(holdingId);
        holdingService.deleteHoldingStocks(userId, id);
        return new BaseResponse<>(null);
    }

    @GetMapping("/{userId}")
    public BaseResponse<List<GetHoldingRes>> getHoldingStocks(@PathVariable int userId, @RequestParam @Nullable String holdingId, @RequestParam @Nullable String stockId){
        log.info("[HoldingController.getHoldingStocks] holdingId = {}, stockId = {}",holdingId,stockId);

        if(stockId!=null) {
            int id = Integer.parseInt(stockId);
            return new BaseResponse<>(holdingService.getHoldingStocksByStockId(userId,id));
        }
        if(holdingId!=null){
            int id = Integer.parseInt(holdingId);
            return new BaseResponse<>(holdingService.getHoldingStocksById(userId,id));
        }
        return new BaseResponse<>(holdingService.getHoldingStocksByUserId(userId));
    }

}
