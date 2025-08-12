package me.quadradev.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

/**
 * Representation of an error returned by the API.
 */
@Value
@Builder
@Schema(description = "Details of an API error response")
public class ErrorResponse {

    @Schema(description = "HTTP status code of the error")
    int code;

    @Schema(description = "Human readable error message")
    String message;

    @Schema(description = "Additional error information")
    Object detail;

    @Schema(description = "Identifier to trace the request")
    String traceId;
}

