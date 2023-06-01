package com.example.kuit_server.mapper;
import com.example.kuit_server.dto.holding.GetHoldingRes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HoldingMapper implements RowMapper<GetHoldingRes> {
    @Override
    public GetHoldingRes mapRow(ResultSet rs, int rowNum) throws SQLException {
        GetHoldingRes getHoldingRes = new GetHoldingRes();

        getHoldingRes.setHoldingId(rs.getInt("holding_id"));
        getHoldingRes.setUserId(rs.getInt("user_id"));
        getHoldingRes.setStockId(rs.getInt("stock_id"));
        getHoldingRes.setStockName(rs.getString("stock_name"));
        getHoldingRes.setPrice(rs.getDouble("price"));
        getHoldingRes.setExchangeRate(rs.getDouble("exchange_rate"));
        getHoldingRes.setQuantity(rs.getInt("quantity"));
        getHoldingRes.setCreatedAt(rs.getTimestamp("CreatedAt"));
        getHoldingRes.setDeletedAt(rs.getTimestamp("DeletedAt"));

        return getHoldingRes;
    }
}
