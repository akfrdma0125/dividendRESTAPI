package com.example.kuit_server.dao;

import com.example.kuit_server.common.exception.StockException;
import com.example.kuit_server.dto.stock.GetStockRes;
import com.example.kuit_server.mapper.StockMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.STOCK_NOT_FOUND;

@Slf4j
@Repository
public class StockDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final StockMapper stockMapper;

    public StockDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.stockMapper = new StockMapper();
    }


    public GetStockRes getUserByStockName(String stockName) {
        String sql = "select * from stock where stock_name=:stockName";
        Map<String, Object> param = Map.of("stockName", stockName);
        try {
            return jdbcTemplate.queryForObject(sql, param, stockMapper);
        }catch(EmptyResultDataAccessException e){
            throw new StockException(STOCK_NOT_FOUND);
        }

    }

    public GetStockRes getUserByStockId(int stockId) {
        String sql = "select * from stock where stock_id=:stockId";
        Map<String, Object> param = Map.of("stockId", stockId);
        try {
            return jdbcTemplate.queryForObject(sql, param, stockMapper);
        }catch(EmptyResultDataAccessException e){
            throw new StockException(STOCK_NOT_FOUND);
        }

    }

}
