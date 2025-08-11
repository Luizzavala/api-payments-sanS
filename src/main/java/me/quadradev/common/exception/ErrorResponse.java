package me.quadradev.common.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
    int code;
    String message;
    Object detail;
    String traceId;
}

