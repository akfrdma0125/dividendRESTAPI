package com.example.kuit_server.dao;

import com.example.kuit_server.common.exception.StockException;
import com.example.kuit_server.dto.holding.GetHoldingRes;
import com.example.kuit_server.dto.holding.PostHoldingReq;
import com.example.kuit_server.dto.user.PostUserReq;
import com.example.kuit_server.dto.user.PostUserRes;
import com.example.kuit_server.mapper.HoldingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.STOCK_NOT_FOUND;

@Slf4j
@Repository
public class HoldingDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final HoldingMapper holdingMapper;

    public HoldingDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.holdingMapper = new HoldingMapper();
    }

    public int getStockId(String stockName){
        String sql = "select stock_id from stock where stock_name=:stockName";
        Map<String, Object> param = Map.of("stockName", stockName);
        try {
            return jdbcTemplate.queryForObject(sql, param, Integer.class);
        }catch(EmptyResultDataAccessException e){
            throw new StockException(STOCK_NOT_FOUND);
        }
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

    public GetHoldingRes getHoldingStockById(int userId, int holdingId) {
        String sql = "select * from holding where user_id=:userId and holding_id=:holdingId";
        Map<String, Object> param = Map.of("userId",userId,"holdingId",holdingId);
        return jdbcTemplate.queryForObject(sql, param, holdingMapper);
    }

    public int deleteHoldingStocks(int userId, int holdingId){
        String sql = "update holding set deletedAt = :now where user_id=:userId and holding_id=:holdingId";
        Map<String, Object> param = Map.of(
                "now", new Timestamp(System.currentTimeMillis()),
                "userId", userId,
                "holdingId",holdingId);
        return jdbcTemplate.update(sql,param);
    }

    public double getAllDividendByHoldingId(int holdingId){
        String sql = "select sum(dividend*quantity) from dividend where holding_id=:holdingId";
        Map<String, Object> param = Map.of("holdingId",holdingId);
        try{
            double result = jdbcTemplate.queryForObject(sql, param, Double.class);
            log.info("[HoldingDao.getAllDividendByHoldingId] money: {}",result);
            return result;
        }catch(NullPointerException e){
            return 0;
        }
    }

    public int deleteAllDividendByHoldingId(int holdingId){
        String sql = "delete from dividend where holding_id=:holdingId";
        Map<String, Object> param = Map.of("holdingId", holdingId);
        return jdbcTemplate.update(sql,param);
    }

}
