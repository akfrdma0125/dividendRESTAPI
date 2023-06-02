package com.example.kuit_server.mapper;


import com.example.kuit_server.common.exception.StockException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.STOCK_DIVIDEND_NOT_FOUND;

public class StockDividendMapper implements RowMapper<Double> {

    @Override
    public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
        double frequency = (double) rs.getInt("frequency");
        double forwardDividend = rs.getDouble("forward_dividend");

        validateForwardDividend(forwardDividend);

        return forwardDividend/frequency;
    }

    private void validateForwardDividend(double forwardDividend){
        if(forwardDividend <= 0){
            throw new StockException(STOCK_DIVIDEND_NOT_FOUND);
        }
    }
}
