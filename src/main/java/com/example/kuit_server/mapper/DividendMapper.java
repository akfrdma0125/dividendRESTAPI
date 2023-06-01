package com.example.kuit_server.mapper;
import com.example.kuit_server.dto.dividend.GetDividendRes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DividendMapper implements RowMapper<GetDividendRes> {
    @Override
    public GetDividendRes mapRow(ResultSet rs, int rowNum) throws SQLException {
        GetDividendRes getDividendRes = new GetDividendRes();

//        getDividendRes.setId(rs.getInt("id"));
//        getDividendRes.setStockname(rs.getString("stockname"));
//        getDividendRes.setDividend(rs.getDouble("dividend"));
//        getDividendRes.setQuantity(rs.getInt("quantity"));
//        getDividendRes.setCheckedAt(rs.getTimestamp("CheckedAt"));
        return getDividendRes;
    }
}
