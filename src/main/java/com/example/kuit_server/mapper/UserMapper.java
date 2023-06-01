package com.example.kuit_server.mapper;

import com.example.kuit_server.dto.user.PostUserRes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<PostUserRes> {
    @Override
    public PostUserRes mapRow(ResultSet rs, int rowNum) throws SQLException {
        PostUserRes postUserRes = new PostUserRes();

        postUserRes.setUserId(rs.getInt("user_id"));
        postUserRes.setUserName(rs.getString("user_name"));
        postUserRes.setEmail(rs.getString("email"));
        postUserRes.setHoldingDollar(rs.getDouble("holding_dollar"));

        return postUserRes;
    }
}
