package com.example.astrotalk.exception;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(String message) {
        super(message);
    }
}
