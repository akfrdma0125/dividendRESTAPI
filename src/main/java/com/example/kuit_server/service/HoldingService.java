package com.example.kuit_server.service;

import com.example.kuit_server.dao.HoldingDao;
import com.example.kuit_server.dao.StockDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HoldingService {
    private final HoldingDao holdingDao;
}
