package com.example.kuit_server.controller;

import com.example.kuit_server.common.exception.UserException;
import com.example.kuit_server.common.response.BaseResponse;
import com.example.kuit_server.dto.user.*;
import com.example.kuit_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.kuit_server.common.response.status.BaseExceptionResponseStatus.INVALID_USER_VALUE;
import static com.example.kuit_server.utils.BindingResultUtils.getErrorMessages;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public BaseResponse<PostUserRes> login(@Validated @RequestBody PostUserReq postUserReq, BindingResult bindingResult) {
        log.info("[UserController.login]");
        if (bindingResult.hasErrors()) {
            throw new UserException(INVALID_USER_VALUE, getErrorMessages(bindingResult));
        }
        return new BaseResponse<>(userService.login(postUserReq));
    }

    @PatchMapping("/{userId}")
    public BaseResponse deleteUser(@PathVariable int userId){
        log.info("[UserController.deleteUser]: userId = {}",userId);
        userService.deleteUser(userId);
        return new BaseResponse(null);
    }

    @GetMapping("/{userId}")
    public BaseResponse<Double> getUserHoldingDollar(@PathVariable int userId){
        log.info("[UserController.getUserHoldingDollar]: userId = {}",userId);
        return new BaseResponse( userService.getUserHoldingDollar(userId));
    }
}
