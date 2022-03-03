package com.example.training.shop.keeper.exceprions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

@Data
public class ApiException {
    private final String massage;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;

    public ApiException(String massage,
                        HttpStatus httpStatus, ZonedDateTime timestamp) {
        this.massage = massage;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }
}
