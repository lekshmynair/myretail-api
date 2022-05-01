package com.myretail.api.exception;

/**
 * Application Exception class
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
