package com.accenture.authentication.dto;

import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ApiError implements Serializable {
  private Integer status;
  private String error;
  private String exception;
  private String message;
  private String timestamp;

  public ApiError(Exception ex, HttpStatus httpStatus) {
    this.status = httpStatus.value();
    this.error = httpStatus.getReasonPhrase();
    this.exception = ex.getClass().getName();
    this.message = ex.getMessage();
    this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT);
  }
}
