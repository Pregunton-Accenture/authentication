package com.accenture.authentication.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UsernameExistExceptionTest {

  @Test
  public void constructor_ShouldReturnNewInstance() {
    assertThat(new UsernameExistException("username")).isInstanceOf(UsernameExistException.class)
        .isNotNull();
  }
}
