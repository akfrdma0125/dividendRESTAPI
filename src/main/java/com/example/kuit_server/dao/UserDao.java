package com.example.kuit_server.dao;

import com.example.kuit_server.dto.user.HoldingDollarInfo;
import com.example.kuit_server.dto.user.PostUserReq;
import com.example.kuit_server.dto.user.PostUserRes;
import com.example.kuit_server.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.userMapper = new UserMapper();
    }

    public int createUser(PostUserReq postUserRequest) {
        String sql = "insert into user(email, user_name) " +
                "values(:email,:userName)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postUserRequest);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public PostUserRes getUserByUserEmail(String email) {
        String sql = "select user_id,user_name,email,holding_dollar from user where email=:email";
        Map<String, Object> param = Map.of("email", email);
        return jdbcTemplate.queryForObject(sql, param, userMapper);
    }

    public int deleteUser(int userId){
        String sql = "delete from user where user_id=:userId";
        Map<String, Object> param = Map.of("userId", userId);
        return jdbcTemplate.update(sql,param);
    }

    public int updateHoldingDollar(HoldingDollarInfo holdingDollarInfo) {
        String sql = "update user set holding_dollar  = holding_dollar+:dollar where user_id= :userId;";
        Map<String, Object> param = Map.of(
                "dollar",holdingDollarInfo.getHoldingDollar(),
                "userId",holdingDollarInfo.getUserId());
        return jdbcTemplate.update(sql,param);
    }

    public boolean hasDuplicateEmail(String email) {
        String sql = "select exists(select email from user where email=:email)";
        Map<String, Object> param = Map.of("email", email);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }

    public boolean hasUserInfobyUserId(int userId) {
        String sql = "select exists(select user_id from user where user_id=:userId)";
        Map<String, Object> param = Map.of("userId", userId);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }
}
