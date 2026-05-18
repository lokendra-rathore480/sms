package com.example.sms.exception;

import com.example.sms.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setStatus(org.springframework.http.HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExistException(AlreadyExistException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.CONFLICT);
        return ResponseEntity.ok(response);
    }
}