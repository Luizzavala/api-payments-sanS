package me.quadradev.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Formato estándar de error")
public record ErrorResponse(
    @Schema(example = "404") Integer status,
    @Schema(example = "Not Found") String error,
    @Schema(example = "Recurso no encontrado") String message,
    @Schema(example = "/api/users/123") String path,
    @Schema(example = "2025-08-13T19:05:00Z") String timestamp
) {}
