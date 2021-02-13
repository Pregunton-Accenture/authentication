package com.accenture.authentication.jwt;

import io.jsonwebtoken.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtTokenTest {

  private static final String USERNAME = "username";
  private static final String EXPIRED_TOKEN =
      "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnaWFuIiwiZXhwIjoxNjEzMDE5NjU2LCJpYXQiOjE2MTMwMTYwNTZ9.O-QVTbTwbQ8jhZcpUt8BklyMb535Wm17wy6V6H8cA-otyu7kcPbJVD7BjiBpBPQndkGuJOZkGl5UEhmlG-2JHw";

  private JwtToken jwtToken;

  @BeforeEach
  public void setUp() {
    jwtToken = new JwtToken();
    ReflectionTestUtils.setField(jwtToken, "secret", "secret");
  }

  @Test
  public void getUsernameFromToken_WhenSendToken_ShouldReturnUsername() {
    String token = jwtToken.generateToken(USERNAME);

    String result = jwtToken.getUsernameFromToken(token);

    assertEquals(USERNAME, result);
  }

  @Test
  public void getUsernameFromToken_WhenSendInvalidToken_ShouldThrowSignatureException() {
    assertThrows(SignatureException.class, () -> jwtToken.getUsernameFromToken(EXPIRED_TOKEN));
  }

  @Test
  public void isTokenExpired_WhenTokenIsNotExpired_ShouldReturnFalse() {
    String token = jwtToken.generateToken(USERNAME);

    assertFalse(jwtToken.isTokenExpired(token));
  }

  @Test
  public void generateToken_WhenUsernameIsProvided_ShouldReturnNewToken() {
    String token = jwtToken.generateToken(USERNAME);

    assertNotNull(token);
  }

  @Test
  public void generateRefreshToken_WhenUsernameIsProvided_ShouldReturnNewRefreshToken() {
    String token = jwtToken.generateRefreshToken(USERNAME);

    assertNotNull(token);
  }
}
