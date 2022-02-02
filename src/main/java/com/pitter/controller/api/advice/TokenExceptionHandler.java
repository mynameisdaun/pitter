package com.pitter.controller.api.advice;

import com.pitter.common.exception.*;
import com.pitter.controller.dto.TokenCode;
import com.pitter.controller.dto.TokenValidateResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity
                .badRequest()
                .body(INVALID_TOKEN_TYPE.toDto());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TokenValidateResponse> handlerMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("handler Exception: {}", e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(INVALID_PARAMETER.toDto());
    }

    @ExceptionHandler(InvalidSubjectEmailException.class)
    public ResponseEntity<TokenValidateResponse> handlerInvalidSubjectEmailException(final InvalidSubjectEmailException e) {
        log.error("handler Exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(INVALID_SUBJECT_EMAIL.toDto());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<TokenValidateResponse> handlerSignatureException(final SignatureException e) {
        log.error("handler Exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(INVALID_SIGNATURE.toDto());
    }

    @ExceptionHandler(UnIdentifiedRefreshTokenException.class)
    public ResponseEntity<TokenValidateResponse> handlerInvalidRefreshTokenException(final UnIdentifiedRefreshTokenException e) {
        log.error("handler Exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(UNIDENTIFIED_REFRESH_TOKEN.toDto());
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<TokenValidateResponse> handlerTokenRefreshException(final TokenRefreshException e) {
        log.error("handler Exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(EXPIRED_REFRESH_TOKEN.toDto());
    }



}
