package com.example.kuit_server.service;

import com.example.kuit_server.common.exception.StockException;
import com.example.kuit_server.dao.StockDao;
import com.example.kuit_server.dto.stock.GetStockRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.STOCK_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {
    private final StockDao stockDao;

    public GetStockRes getStockById(int stockId){
        log.info("[StockService.getStockById]: stockId = {}",stockId);

        //TODO : 1. 해당 주식  정보 조회
        GetStockRes getStockRes = stockDao.getUserByStockId(stockId);

        //TODO : 2. 정보가 있는지 확인
        validateStockInfo(getStockRes);

        return getStockRes;
    }

    public GetStockRes getStockByName(String stockName){
        log.info("[StockService.getStockById]: stockName = {}",stockName);

        //TODO : 1. 해당 주식  정보 조회
        GetStockRes getStockRes = stockDao.getUserByStockName(stockName);

        //TODO : 2. 정보가 있는지 확인
        validateStockInfo(getStockRes);

        return getStockRes;
    }

    private void validateStockInfo(GetStockRes getStockRes) {
        if(getStockRes == null){
            throw new StockException(STOCK_NOT_FOUND);
        }
    }

}
