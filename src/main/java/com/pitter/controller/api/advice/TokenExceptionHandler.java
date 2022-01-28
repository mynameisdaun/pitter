package com.pitter.controller.api.advice;

import com.pitter.controller.dto.TokenValidateResponse;
import com.pitter.common.exception.TokenTypeException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.pitter.controller.dto.TokenCode.*;

@RestControllerAdvice @Slf4j
public class TokenExceptionHandler {

    @ExceptionHandler(TokenTypeException.class)
    public ResponseEntity<TokenValidateResponse> handlerTokenTypeException(final TokenTypeException e) {
        log.error("handler Exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(INVALID_TOKEN_TYPE.toDto());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TokenValidateResponse> handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("handler Exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(INVALID_PARAMETER.toDto());
    }

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<TokenValidateResponse> handlerJwtException(final ExpiredJwtException e) {
//        log.error("handler Exception: {}", e.getMessage());
//        return ResponseEntity.badRequest().body(EXPIRED_ACCESS_TOKEN.toDto());
//    }
}
