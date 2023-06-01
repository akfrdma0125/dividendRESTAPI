package com.example.kuit_server.dao;

import com.example.kuit_server.dto.holding.GetHoldingRes;
import com.example.kuit_server.dto.holding.PostHoldingReq;
import com.example.kuit_server.dto.user.PostUserReq;
import com.example.kuit_server.dto.user.PostUserRes;
import com.example.kuit_server.mapper.HoldingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class HoldingDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final HoldingMapper holdingMapper;

    public HoldingDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.holdingMapper = new HoldingMapper();
    }

    public int createHoldingStock(PostHoldingReq postHoldingReq) {
        String sql = "insert into holding(user_id, stock_id, stock_name, price, exchange_rate, quantity) " +
                "values(:userId,:stockId,:stockName,:price,:exchangeRate,:quantity)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postHoldingReq);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public List<GetHoldingRes> getHoldingStocksByUserId(int userId) {
        String sql = "select * from holding where user_id=:userId and deletedat IS NULL";
        Map<String, Object> param = Map.of("userId",userId);
        return jdbcTemplate.query(sql, param, holdingMapper);
    }

    public List<GetHoldingRes> getHoldingStocksById(int userId, int holdingId) {
        String sql = "select * from holding where user_id=:userId and holding_id=:holdingId";
        Map<String, Object> param = Map.of("userId",userId,"holdingId",holdingId);
        return jdbcTemplate.query(sql, param, holdingMapper);
    }

    public List<GetHoldingRes> getHoldingStocksByStockId(int userId, int stockId) {
        String sql = "select * from holding where user_id=:userId and stock_id=:stockId";
        Map<String, Object> param = Map.of("userId",userId,"stockId",stockId);
        return jdbcTemplate.query(sql, param, holdingMapper);
    }

    public int deleteHoldingStocks(int userId, int stockId){
        String sql = "update holding set deletedAt = :now where user_id=:userId and stock_id=:stockId";
        Map<String, Object> param = Map.of(
                "now", new Timestamp(System.currentTimeMillis()),
                "userId", userId,
                "stockId",stockId);
        return jdbcTemplate.update(sql,param);
    }

//    public boolean hasHoldingStock(String email) {
//        String sql = "select exists(select email from holding where email=:email)";
//        Map<String, Object> param = Map.of("email", email);
//        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
//    }
}
