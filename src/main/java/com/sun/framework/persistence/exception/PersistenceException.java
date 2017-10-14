package com.sun.framework.persistence.exception;

public class PersistenceException extends RuntimeException {
    private static final long serialVersionUID = -8834069467951926310L;

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
