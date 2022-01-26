package com.pitter.controller.api.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class TokenExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handlerRuntimeException(final RuntimeException e) {
        log.error("handler Exception: {}", e.getMessage());
        return e.getMessage();
    }

}
