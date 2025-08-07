package me.quadradev.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.quadradev.common.util.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<?>> handleApiException(ApiException ex) {
        ApiResponse<?> body = ApiResponse.error(
                ex.getStatus().value(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(ex.getStatus()).body(body);
    }
}
