package com.example.kuit_server.dao;

import com.example.kuit_server.common.exception.DividendException;
import com.example.kuit_server.common.exception.StockException;
import com.example.kuit_server.dto.dividend.GetDividendRes;
import com.example.kuit_server.dto.dividend.PostDividendReq;
import com.example.kuit_server.dto.holding.GetHoldingRes;
import com.example.kuit_server.dto.holding.PostHoldingReq;
import com.example.kuit_server.dto.stock.StockInfo;
import com.example.kuit_server.dto.user.HoldingDollarInfo;
import com.example.kuit_server.mapper.DividendMapper;
import com.example.kuit_server.mapper.StockDividendMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.DIVIDEND_NOT_FOUND;
import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.STOCK_NOT_FOUND;

@Slf4j
@Repository
public class DividendDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final StockDividendMapper stockDividendMapper;
    private final DividendMapper dividendMapper;

    public DividendDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.stockDividendMapper = new StockDividendMapper();
        this.dividendMapper = new DividendMapper();
    }

    //Todo: 1. stock 배당금 정보 가져오는 함수
    public StockInfo getStockDividend(int stockId){
        String sql = "select forward_dividend, frequency, stock_name from stock where stock_id=:stockId";
        Map<String, Object> param = Map.of("stockId", stockId);
        try {
            return jdbcTemplate.queryForObject(sql, param, stockDividendMapper);
        }catch(EmptyResultDataAccessException e){
            throw new StockException(STOCK_NOT_FOUND);
        }
    }
    //Todo: 2. 배당금 정보를 등록하는 함수
    public int createDividendLog(PostDividendReq postDividendReq) {
        String sql = "insert into dividend(user_id, holding_id, stock_id, stock_name, quantity, dividend) " +
                "values(:userId,:holdingId,:stockId,:stockName,:quantity,:dividend)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(postDividendReq);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, param, keyHolder);


        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    public boolean hasValidateHolding(int holdingId) {
        String sql = "select exists(select holding_id from holding where holding_id=:holdingId and deletedat IS NULL)";
        Map<String, Object> param = Map.of("holdingId", holdingId);
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, param, boolean.class));
    }
    //Todo: 3. 배당금 정보를 원하는 조건으로 가져오는 함수
    public List<GetDividendRes> getDividendLogByUserId(int userId) {
        String sql = "select * from dividend where user_id=:userId";
        Map<String, Object> param = Map.of("userId",userId);
        return jdbcTemplate.query(sql, param, dividendMapper);
    }

    public List<GetDividendRes> getDividendLogByHoldingId(int userId, int holdingId) {
        String sql = "select * from dividend where user_id=:userId and holding_id=:holdingId";
        Map<String, Object> param = Map.of("userId",userId,"holdingId",holdingId);
        try {
            return jdbcTemplate.query(sql, param, dividendMapper);
        }catch (EmptyResultDataAccessException e){
            throw new DividendException(DIVIDEND_NOT_FOUND);
        }
    }

    public List<GetDividendRes> getDividendLogByStockId(int userId, int stockId) {
        String sql = "select * from dividend where user_id=:userId and stock_id=:stockId";
        Map<String, Object> param = Map.of("userId",userId,"stockId",stockId);
        try{
            return jdbcTemplate.query(sql, param, dividendMapper);
        }catch (EmptyResultDataAccessException e){
            throw new DividendException(DIVIDEND_NOT_FOUND);
        }

    }
}
