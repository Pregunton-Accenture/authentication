package com.accenture.authentication.exception;

public class UsernameExistsException extends Exception {

  public UsernameExistsException(String username) {
    super("There is an account with the username: " + username);
  }
}
