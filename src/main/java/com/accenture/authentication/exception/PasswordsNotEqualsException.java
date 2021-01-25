package com.accenture.authentication.exception;

public class PasswordsNotEqualsException extends Exception {

  public PasswordsNotEqualsException() {
    super("The provided passwords are not equals");
  }
}
