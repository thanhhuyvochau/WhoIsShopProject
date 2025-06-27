package org.project.usermanagement.exception;

import org.project.usermanagement.dto.response.ApiResponse;
import org.project.usermanagement.exception.types.EmailDuplicateException;
import org.project.usermanagement.exception.types.PhoneDuplicateException;
import org.project.usermanagement.exception.types.UserNameDuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessages
                    .append("[")
                    .append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("] ");
        });
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(errorMessages.toString().trim(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + ex.getMessage());
    }

    @ExceptionHandler({UserNameDuplicateException.class, EmailDuplicateException.class, PhoneDuplicateException.class})
    public ResponseEntity<Object> handleUserException(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.error(message, status.value()));
    }
}
