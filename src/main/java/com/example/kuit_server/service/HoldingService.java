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
        updateHoldingDollar(postHoldingReq.getPrice()*postHoldingReq.getQuantity(), postHoldingReq.getUserId());
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

    public void deleteHoldingStocks(int userId, int holdingId){
        log.info("[HoldingService.deleteHoldingStock]");
        int affectedRow = holdingDao.deleteHoldingStocks(userId,holdingId);
        if(affectedRow < 1){
            throw new HoldingException(HOLDING_NOT_FOUND);
        }
        double price = calculateTotalPrice(userId, holdingId)*(-1);
        updateHoldingDollar(price,userId);
        holdingDao.deleteAllDividendByHoldingId(holdingId);
    }

    private double calculateTotalPrice(int userId, int holdingId) {
        double totalDividend = holdingDao.getAllDividendByHoldingId(holdingId);
        GetHoldingRes target = holdingDao.getHoldingStockById(userId, holdingId);
        double stockPrice = target.getPrice()*target.getQuantity();
        return totalDividend+stockPrice;
    }

    private void updateHoldingDollar(double price, int userId) {
        userService.updateHoldingDollar(new HoldingDollarInfo(userId,price));
    }
}
