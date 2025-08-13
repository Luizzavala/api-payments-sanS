package me.quadradev.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Formato estándar de error")
public class ErrorResponse {
  @Schema(example = "404") private Integer status;
  @Schema(example = "Not Found") private String error;
  @Schema(example = "Recurso no encontrado") private String message;
  @Schema(example = "/api/users/123") private String path;
  @Schema(example = "2025-08-13T19:05:00Z") private String timestamp;

  public Integer getStatus() { return status; }
  public void setStatus(Integer status) { this.status = status; }
  public String getError() { return error; }
  public void setError(String error) { this.error = error; }
  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
  public String getPath() { return path; }
  public void setPath(String path) { this.path = path; }
  public String getTimestamp() { return timestamp; }
  public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
