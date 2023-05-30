package com.example.kuit_server.dto;

import lombok.Getter;

@Getter
public class PostUserRes {
    private int userId;
    private String jwt = null;

    public PostUserRes(int userId) {
        this.userId = userId;
    }
}
