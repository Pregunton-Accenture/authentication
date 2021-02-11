package com.accenture.authentication.controller.advice;

import com.accenture.authentication.exception.TokenExpiredException;
import com.accenture.pojo.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AuthenticationControllerAdvice extends ResponseEntityExceptionHandler {

  private ResponseEntity<SimpleResponse> buildSimpleResponse(String message, HttpStatus httpStatus) {
    return ResponseEntity.status(httpStatus)
        .body(SimpleResponse.builder()
            .message(message)
            .status(httpStatus.value())
            .build());
  }

  @ExceptionHandler(value = {
      AuthenticationException.class,
      UsernameNotFoundException.class,
      TokenExpiredException.class
  })
  protected ResponseEntity<SimpleResponse> unauthorizedExceptionHandler(Exception ex) {
    return buildSimpleResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(NullPointerException.class)
  protected ResponseEntity<SimpleResponse> nullPointerExceptionHandler(NullPointerException ex) {
    return buildSimpleResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<SimpleResponse> unexpectedErrorHandler(Exception ex) {
    return buildSimpleResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
