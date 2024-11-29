package com.example.demo.config;

import com.example.demo.exception.PasswordInvalid;
import com.example.demo.model.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PasswordInvalid.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            PasswordInvalid ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }
}
