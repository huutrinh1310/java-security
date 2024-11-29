package com.example.demo.exception;

public class PasswordInvalid extends RuntimeException {
    public PasswordInvalid(String message) {
        super(message);
    }
}
