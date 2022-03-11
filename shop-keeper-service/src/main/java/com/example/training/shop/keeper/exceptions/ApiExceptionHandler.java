package com.example.training.shop.keeper.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = {ItemNotFoundException.class})
    public ResponseEntity<Object> handleApiNotFoundException(ItemNotFoundException e) {
        logger.error("Item with the specified code was not found");
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ApiExceptionTemplate apiException = new ApiExceptionTemplate(
                e.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("GMT+3"))
        );
        return new ResponseEntity<>(apiException, notFound);
    }

}
