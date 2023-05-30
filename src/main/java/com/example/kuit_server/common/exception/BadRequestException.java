package com.example.kuit_server.common.exception;

import com.example.kuit_server.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public BadRequestException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

}