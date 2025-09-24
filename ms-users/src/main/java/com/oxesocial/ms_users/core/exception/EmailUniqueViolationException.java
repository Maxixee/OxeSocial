package com.oxesocial.ms_users.core.exception;

public class EmailUniqueViolationException extends RuntimeException {
    public EmailUniqueViolationException(String message) {
        super(message);
    }
}
