package com.openthread.api.web.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ProblemResponse> handleApi(ApiException exception, HttpServletRequest request) {
        return ResponseEntity.status(exception.getStatus())
                .contentType(MediaType.valueOf("application/problem+json"))
                .body(new ProblemResponse(
                        exception.getType(),
                        exception.getTitle(),
                        exception.getStatus().value(),
                        exception.getMessage(),
                        request.getRequestURI(),
                        Map.of()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemResponse> handleValidation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        Map<String, List<String>> fieldErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fieldErrors.computeIfAbsent(fieldError.getField(), key -> new java.util.ArrayList<>())
                    .add(fieldError.getDefaultMessage() == null ? "invalid" : fieldError.getDefaultMessage());
        }
        errors.putAll(fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.valueOf("application/problem+json"))
                .body(new ProblemResponse(
                        "https://openthread.dev/problems/validation",
                        "Validation Failed",
                        HttpStatus.BAD_REQUEST.value(),
                        "Request validation failed",
                        request.getRequestURI(),
                        errors
                ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemResponse> handleConstraint(ConstraintViolationException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.valueOf("application/problem+json"))
                .body(new ProblemResponse(
                        "https://openthread.dev/problems/validation",
                        "Validation Failed",
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        request.getRequestURI(),
                        Map.of()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemResponse> handleUnknown(Exception exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.valueOf("application/problem+json"))
                .body(new ProblemResponse(
                        "https://openthread.dev/problems/internal",
                        "Internal Server Error",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Unexpected error",
                        request.getRequestURI(),
                        Map.of("message", exception.getMessage())
                ));
    }
}
