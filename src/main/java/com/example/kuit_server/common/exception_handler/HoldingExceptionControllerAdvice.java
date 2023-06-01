package com.example.kuit_server.common.exception_handler;

import com.example.kuit_server.common.exception.HoldingException;
import com.example.kuit_server.common.response.BaseErrorResponse;
import jakarta.annotation.Priority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Priority(0)
@RestControllerAdvice
public class HoldingExceptionControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HoldingException.class)
    public BaseErrorResponse runtimeExceptionHandler(HoldingException e){
        log.error("[handle_RestaurantException]",e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
