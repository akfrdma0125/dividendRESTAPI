package com.example.kuit_server.controller;

import com.example.kuit_server.common.exception.DividendException;
import com.example.kuit_server.common.exception.HoldingException;
import com.example.kuit_server.common.response.BaseResponse;
import com.example.kuit_server.dto.dividend.GetDividendRes;
import com.example.kuit_server.dto.dividend.PostDividendReq;
import com.example.kuit_server.dto.holding.GetHoldingRes;
import com.example.kuit_server.dto.holding.PostHoldingReq;
import com.example.kuit_server.service.DividendService;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.INVALID_DIVIDEND_VALUE;
import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.INVALID_HOLDING_VALUE;
import static com.example.kuit_server.utils.BindingResultUtils.getErrorMessages;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/dividends")
public class DividendController {
    private final DividendService dividendService;

    @PostMapping("")
    public BaseResponse createDividendLog(@Validated @RequestBody PostDividendReq postDividendReq, BindingResult bindingResult){
        log.info("[DividendController.createDividendLog]");
        if(bindingResult.hasErrors()){
            throw new DividendException(INVALID_DIVIDEND_VALUE, getErrorMessages(bindingResult));
        }
        dividendService.createDividendLog(postDividendReq);
        return new BaseResponse(null);
    }

    @GetMapping("/{userId}")
    public BaseResponse<List<GetDividendRes>> getDividendLog(@PathVariable int userId, @RequestParam @Nullable String holdingId, @RequestParam @Nullable String stockId){
        log.info("[DividendController.getDividendLog] holdingId = {}, stockId = {}",holdingId,stockId);

        if(stockId!=null) {
            int id = Integer.parseInt(stockId);
            return new BaseResponse<>(dividendService.getDividendLogByStockId(userId,id));
        }
        if(holdingId!=null){
            int id = Integer.parseInt(holdingId);
            return new BaseResponse<>(dividendService.getDividendLogByHoldingId(userId,id));
        }
        return new BaseResponse<>(dividendService.getDividendLogByUserId(userId));
    }

}
