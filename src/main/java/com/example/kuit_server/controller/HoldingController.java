package com.example.kuit_server.controller;

import com.example.kuit_server.service.HoldingService;
import com.example.kuit_server.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class HoldingController {
    private final HoldingService holdingService;
}
