package org.example.idealstore.exception.custom;

public class CodigoUniqueViolationException extends RuntimeException {
    public CodigoUniqueViolationException(String message) {
        super(message);
    }
}
