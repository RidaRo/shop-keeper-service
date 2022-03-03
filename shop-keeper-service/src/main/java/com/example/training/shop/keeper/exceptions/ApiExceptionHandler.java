package com.example.training.shop.keeper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ItemNotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(ItemNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiExceptionTemplate apiException = new ApiExceptionTemplate(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("GMT+3"))
        );
        return new ResponseEntity<>(apiException, notFound);
    }

}
