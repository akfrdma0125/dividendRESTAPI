package com.example.kuit_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLoginRes {
    private long userId;
    private String jwt;
}
