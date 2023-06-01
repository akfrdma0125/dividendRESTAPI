package com.example.kuit_server.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class PostUserRes {
    private int userId;
    private String userName;
    private String email;
    private double holdingDollar;
}
