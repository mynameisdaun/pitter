package com.pitter.common.exception;

public class TokenTypeException extends RuntimeException {
    public TokenTypeException() {
        super();
    }

    public TokenTypeException(String message) {
        super(message);
    }

    public TokenTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenTypeException(Throwable cause) {
        super(cause);
    }
}
