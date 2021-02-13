package com.accenture.authentication.exception;

public class UsernameExistException extends RuntimeException {

  public UsernameExistException(String username) {
    super("Username " + username + " already exists.");
  }
}
