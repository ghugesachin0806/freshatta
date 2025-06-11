package com.kisanbasket.freshatta.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> jsonProcessingExceptionHandler(JsonProcessingException e)
    {
        ErrorResponse errorResponse = ErrorResponse.builder().
                status(HttpStatus.BAD_REQUEST)
                .message("Invalid input format")
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentValidationHandler(MethodArgumentNotValidException e)
    {
        ErrorResponse errorResponse = ErrorResponse.builder().
                status(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .timestamp(new Date())
                .errors(e.getAllErrors())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e)
    {
        ErrorResponse errorResponse = ErrorResponse.builder().
                status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("An unexpected error occurred")
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message("Failed to process the image file: " + e.getMessage())
                .timestamp(new Date())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}