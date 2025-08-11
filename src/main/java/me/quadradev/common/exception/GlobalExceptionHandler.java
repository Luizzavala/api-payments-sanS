package me.quadradev.common.exception;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        String traceId = UUID.randomUUID().toString();
        log.error("API exception traceId={}", traceId, ex);
        ErrorResponse body = ErrorResponse.builder()
                .code(ex.getStatus().value())
                .message(ex.getMessage())
                .detail(null)
                .traceId(traceId)
                .build();
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        String traceId = UUID.randomUUID().toString();
        log.error("Unexpected exception traceId={}", traceId, ex);
        ErrorResponse body = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Internal server error")
                .detail(ex.getMessage())
                .traceId(traceId)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}

