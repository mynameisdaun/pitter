package com.pitter.common.exception;

public class UnIdentifiedRefreshTokenException extends RuntimeException {
    public UnIdentifiedRefreshTokenException() {
        super();
    }

    public UnIdentifiedRefreshTokenException(String message) {
        super(message);
    }

    public UnIdentifiedRefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnIdentifiedRefreshTokenException(Throwable cause) {
        super(cause);
    }
}
