package com.example.kuit_server.mapper;


import com.example.kuit_server.common.exception.StockException;
import com.example.kuit_server.dto.stock.StockInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.STOCK_DIVIDEND_NOT_FOUND;

public class StockDividendMapper implements RowMapper<StockInfo> {

    @Override
    public StockInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        double frequency = (double) rs.getInt("frequency");
        double forwardDividend = rs.getDouble("forward_dividend");
        String stockName = rs.getString("stock_name");

        validateForwardDividend(forwardDividend);

        return new StockInfo(forwardDividend/frequency, stockName);
    }

    private void validateForwardDividend(double forwardDividend){
        if(forwardDividend <= 0){
            throw new StockException(STOCK_DIVIDEND_NOT_FOUND);
        }
    }
}
