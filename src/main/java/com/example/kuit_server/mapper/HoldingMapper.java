package com.example.kuit_server.mapper;
import com.example.kuit_server.dto.holding.GetHoldingRes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HoldingMapper implements RowMapper<GetHoldingRes> {
    @Override
    public GetHoldingRes mapRow(ResultSet rs, int rowNum) throws SQLException {
        GetHoldingRes getHoldingRes = new GetHoldingRes();
//
//        getHoldingRes.setId(rs.getInt("id"));
//        getHoldingRes.setUserid(rs.getInt("userid"));
//        getHoldingRes.setStockid(rs.getInt("stockid"));
//        getHoldingRes.setStockname(rs.getString("stockname"));
//        getHoldingRes.setPrice(rs.getDouble("price"));
//        getHoldingRes.setExrate(rs.getDouble("exrate"));
//        getHoldingRes.setQuantity(rs.getInt("quantity"));
//        getHoldingRes.setCreatedAt(rs.getTimestamp("CreatedAt"));
//        getHoldingRes.setDeletedAt(rs.getTimestamp("DeletedAt"));
        return getHoldingRes;
    }
}
