package org.com.dianping.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to handle exceptions across the application.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link RuntimeException} and returns a structured error response.
     *
     * @param e the runtime exception
     * @return a {@link ResponseEntity} containing the error message
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    /**
     * A record to encapsulate error response details.
     *
     * @param message the error message
     */
    record ErrorResponse(String message) {
    }
}