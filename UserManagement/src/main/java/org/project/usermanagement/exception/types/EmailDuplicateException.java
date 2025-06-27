package org.project.usermanagement.exception.types;

public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException() {
        super("Email already exists, please try a new one.");
    }
}
