package com.example.kuit_server.mapper;

import com.example.kuit_server.common.exception.StockException;
import com.example.kuit_server.dto.stock.GetStockRes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.STOCK_DIVIDEND_NOT_FOUND;


public class StockMapper implements RowMapper<GetStockRes> {
    @Override
    public GetStockRes mapRow(ResultSet rs, int rowNum) throws SQLException {
        GetStockRes getStockRes = new GetStockRes();

        double frequency = (double) rs.getInt("frequency");
        double forwardDividend = rs.getDouble("forward_dividend");

        validateForwardDividend(forwardDividend);

        getStockRes.setStockId(rs.getInt("stock_id"));
        getStockRes.setStockName(rs.getString("stock_name"));
        getStockRes.setUpdatedAt(rs.getTimestamp("updated_at"));
        getStockRes.setActualDividend(forwardDividend/frequency);

        return getStockRes;
    }

    private void validateForwardDividend(double forwardDividend){
        if(forwardDividend <= 0){
            throw new StockException(STOCK_DIVIDEND_NOT_FOUND);
        }
    }
}
