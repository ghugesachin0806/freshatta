package com.kisanbasket.freshatta.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().
                status(e.getStatus())
                .message(e.getMessage())
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<?> jsonProcessingExceptionHandler(JsonProcessingException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().
                status(HttpStatus.BAD_REQUEST)
                .message("Invalid input format")
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentValidationHandler(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.builder().
                status(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .timestamp(new Date())
                .errors(e.getAllErrors())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.builder().
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("An unexpected error occurred")
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String message = "Database constraint violation";

        if (e.getCause() != null && e.getCause().getMessage() != null &&
                e.getCause().getMessage().contains("duplicate key")) {
            message = "Name already exists";
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .message(message)
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}