package com.accenture.authentication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {
  private String username;
  private String password;
  private String confirmPassword;

  /**
   * Validates if password and confirmPassword are equals.
   *
   * @return <b>true</b> if password and confirm are not null and both are equals; otherwise <b>false</b>
   */
  public boolean arePasswordsEquals() {
    return Objects.nonNull(password) && Objects.nonNull(confirmPassword) && Objects.equals(password, confirmPassword);
  }
}
