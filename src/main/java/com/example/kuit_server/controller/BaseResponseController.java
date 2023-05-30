package com.example.kuit_server.controller;

import com.example.kuit_server.common.response.BaseResponse;
import com.example.kuit_server.temp.UserData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseResponseController {

    @RequestMapping("/base-response")
    public BaseResponse<UserData> checkBaseResponse(){
        UserData aralla = new UserData("aralla",23);
        return new BaseResponse<>(aralla);
    }
}
