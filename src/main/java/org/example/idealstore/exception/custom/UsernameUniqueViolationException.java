package org.example.idealstore.exception.custom;

public class UsernameUniqueViolationException extends RuntimeException {

    public UsernameUniqueViolationException(String message){
        super(message);
    }
}
