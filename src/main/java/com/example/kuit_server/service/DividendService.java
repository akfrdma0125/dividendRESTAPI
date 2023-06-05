package com.example.kuit_server.service;

import com.example.kuit_server.common.exception.HoldingException;
import com.example.kuit_server.common.exception.StockException;
import com.example.kuit_server.dao.DividendDao;
import com.example.kuit_server.dao.StockDao;
import com.example.kuit_server.dto.dividend.GetDividendRes;
import com.example.kuit_server.dto.dividend.PostDividendReq;
import com.example.kuit_server.dto.holding.PostHoldingReq;
import com.example.kuit_server.dto.stock.StockInfo;
import com.example.kuit_server.dto.user.HoldingDollarInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.DELETED_HOLDING_VALUE;
import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.STOCK_DIVIDEND_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class DividendService {
    private final DividendDao dividendDao;
    private final UserService userService;

    public void createDividendLog(PostDividendReq postDividendReq){
        log.info("[DividendService.createDividendLog]");
        //TODO: 1. 보유 주식 정보 확인
        validateHolding(postDividendReq.getHoldingId());

        //TODO : 2. 배당금 정보 추가
        StockInfo stockInfo = dividendDao.getStockDividend(postDividendReq.getStockId());
        postDividendReq.setDividend(stockInfo.getForwardDividend());
        log.info("[DividendService.createDividendLog]: stockname={}",stockInfo.getStockName());
        postDividendReq.setStockName(stockInfo.getStockName());


        log.info(postDividendReq.toString());

        //TODO: 3. 데이터 추가
        int id = dividendDao.createDividendLog(postDividendReq);

        //TODO: 4. 유저 테이블 데이터  반영
        updateHoldingDollar(postDividendReq.getDividend(),postDividendReq.getQuantity(),postDividendReq.getUserId());
    }

    public List<GetDividendRes> getDividendLogByUserId(int userId) {
        log.info("[DividendService.getDividendLogByUserId]");
        return dividendDao.getDividendLogByUserId(userId);
    }

    public List<GetDividendRes> getDividendLogByHoldingId(int userId, int holdingId) {
        log.info("[DividendService.getDividendLogByHoldingId]");
        return dividendDao.getDividendLogByHoldingId(userId,holdingId);
    }

    public List<GetDividendRes> getDividendLogByStockId(int userId, int stockId) {
        log.info("[DividendService.getDividendLogByStockId]");
        return dividendDao.getDividendLogByStockId(userId,stockId);
    }

    private void validateHolding(int holdingId) {
        if(!dividendDao.hasValidateHolding(holdingId)){
            throw new HoldingException(DELETED_HOLDING_VALUE);
        }
    }

    private void updateHoldingDollar(double price, int quantity, int userId) {
        double totalPrice = price*quantity;
        userService.updateHoldingDollar(new HoldingDollarInfo(userId,totalPrice));
    }

}
