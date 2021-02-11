package com.accenture.authentication.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtTokenTest {

  private JwtToken jwtToken;

  @BeforeEach
  public void setUp() {
    jwtToken = new JwtToken();

    ReflectionTestUtils.setField(jwtToken, "secret", "secret");
  }

  @Test
  public void as() {
    jwtToken.getUsernameFromToken("username");
  }
}
