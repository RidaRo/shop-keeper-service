package com.example.training.shop.keeper.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
class ApiExceptionTemplate {
    private final String massage;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiExceptionTemplate(String massage,
                                HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.massage = massage;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
