package com.pitter.common.exception;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException() {
        super();
    }

    public TokenRefreshException(String message) {
        super(message);
    }

    public TokenRefreshException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenRefreshException(Throwable cause) {
        super(cause);
    }
}
