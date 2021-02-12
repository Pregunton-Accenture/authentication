package com.accenture.authentication.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenExpiredExceptionTest {

  @Test
  public void constructor_ShouldReturnNewInstance() {
    assertThat(new TokenExpiredException()).isInstanceOf(TokenExpiredException.class)
        .isNotNull();
  }
}
