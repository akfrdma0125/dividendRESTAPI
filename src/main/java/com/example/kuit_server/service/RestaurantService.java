package com.example.kuit_server.service;

import com.example.kuit_server.dao.RestaurantDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantDao restaurantDao;
}
