package com.pitter.controller.api.advice;

import com.pitter.common.exception.InvalidSubjectEmailException;
import com.pitter.controller.dto.TokenValidateResponse;
import com.pitter.common.exception.TokenTypeException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
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

    @ExceptionHandler(InvalidSubjectEmailException.class)
    public ResponseEntity<TokenValidateResponse> handlerInvalidSubjectEmailException(final InvalidSubjectEmailException e) {
        log.error("handler Exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(INVALID_SUBJECT_EMAIL.toDto());
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<TokenValidateResponse> handlerSignatureException(final SignatureException e) {
        log.error("handler Exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(INVALID_SIGNATURE.toDto());
    }
}
