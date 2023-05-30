package com.example.kuit_server.common.exception;

import com.example.kuit_server.common.response.status.ResponseStatus;
import lombok.Getter;

@Getter
public class InternalServerErrorException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public InternalServerErrorException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }

}