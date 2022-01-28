package com.pitter.common.exception;

public class InvalidSubjectEmailException extends RuntimeException{
    public InvalidSubjectEmailException() {
        super();
    }

    public InvalidSubjectEmailException(String message) {
        super(message);
    }

    public InvalidSubjectEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSubjectEmailException(Throwable cause) {
        super(cause);
    }
}
