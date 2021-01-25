package com.accenture.authentication.controller.advice;

import com.accenture.authentication.dto.ApiError;
import com.accenture.authentication.exception.PasswordsNotEqualsException;
import com.accenture.authentication.exception.UsernameExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationControllerAdvice {

  @ExceptionHandler(PasswordsNotEqualsException.class)
  public ResponseEntity<ApiError> handlePasswordsNotEqualsException(PasswordsNotEqualsException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<ApiError>(new ApiError(ex, status), status);
  }

  @ExceptionHandler(UsernameExistsException.class)
  public ResponseEntity<ApiError> handleUsernameExistsException(UsernameExistsException ex) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return new ResponseEntity<ApiError>(new ApiError(ex, status), status);
  }
}
