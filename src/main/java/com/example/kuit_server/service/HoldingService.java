package com.example.kuit_server.service;

import com.example.kuit_server.common.exception.DatabaseException;
import com.example.kuit_server.common.exception.HoldingException;
import com.example.kuit_server.dao.HoldingDao;
import com.example.kuit_server.dao.StockDao;
import com.example.kuit_server.dto.holding.GetHoldingRes;
import com.example.kuit_server.dto.holding.PostHoldingReq;
import com.example.kuit_server.dto.user.HoldingDollarInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.DATABASE_ERROR;
import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.HOLDING_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class HoldingService {
    private final HoldingDao holdingDao;
    private final UserService userService;

    public void createHoldingStock(PostHoldingReq postHoldingReq){
        log.info("[HoldingService.createHoldingStock]");
        int id = holdingDao.createHoldingStock(postHoldingReq);
        log.info("[HoldingService.createHoldingStock] : holdingId = {}",id);
        updateHoldingDollar(postHoldingReq);
    }

    public List<GetHoldingRes> getHoldingStocksByUserId(int userId){
        log.info("[HoldingService.getHoldingStocksByUserId]: userId = ",userId);
        return holdingDao.getHoldingStocksByUserId(userId);
    }

    public List<GetHoldingRes> getHoldingStocksByStockId(int userId, int stockId){
        log.info("[HoldingService.getHoldingStocksByStockId]: stockId = ",stockId);
        return holdingDao.getHoldingStocksByStockId(userId, stockId);
    }

    public List<GetHoldingRes> getHoldingStocksById(int userId, int holdingId){
        log.info("[HoldingService.getHoldingStocksById]: holdingId = ",holdingId);
        return holdingDao.getHoldingStocksById(userId, holdingId);
    }

    public int deleteHoldingStocks(int userId, int stockId){
        log.info("[HoldingService.deleteHoldingStock]");
        int affectedRow = holdingDao.deleteHoldingStocks(userId,stockId);
        if(affectedRow < 1){
            throw new HoldingException(HOLDING_NOT_FOUND);
        }
        return affectedRow;
    }

    private void updateHoldingDollar(PostHoldingReq postHoldingReq) {
        double totalPrice = postHoldingReq.getPrice()* postHoldingReq.getQuantity();
        userService.updateHoldingDollar(new HoldingDollarInfo(postHoldingReq.getUserId(),totalPrice));
    }
}
