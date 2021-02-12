package com.accenture.authentication.service;

import com.accenture.authentication.exception.TokenExpiredException;
import com.accenture.authentication.jwt.JwtToken;
import com.accenture.model.UserCredentials;
import com.accenture.pojo.TokenDto;
import com.accenture.pojo.UserCredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(SpringExtension.class)
public class AuthenticationServiceTest {

  private static final String USERNAME = "user";
  private static final String PASSWORD = "pass";
  private static final String TOKEN = "token";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final UserCredentialsDTO CREDENTIALS_DTO = new UserCredentialsDTO(USERNAME, PASSWORD);
  private static final UserCredentials CREDENTIALS = new UserCredentials(USERNAME, PASSWORD);

  @InjectMocks
  private AuthenticationService authenticationService;
  @Mock
  private JwtToken jwtToken;
  @Mock
  private ObjectMapper objectMapper;

  @Test
  void authenticate_WhenUserCredentialsAreValid_ShouldReturnTokenDto() {
    doReturn(CREDENTIALS).when(objectMapper)
        .convertValue(CREDENTIALS_DTO, UserCredentials.class);
    doReturn(TOKEN).when(jwtToken)
        .generateToken(USERNAME);
    doReturn(REFRESH_TOKEN).when(jwtToken)
        .generateRefreshToken(USERNAME);

    TokenDto result = authenticationService.authenticate(CREDENTIALS_DTO);

    assertEquals(TOKEN, result.getToken());
    assertEquals(REFRESH_TOKEN, result.getRefreshToken());
  }

  @Test
  void authenticate_WhenUserCredentialsAreNull_ShouldThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> authenticationService.authenticate(CREDENTIALS_DTO));
  }

  @Test
  void refresh_WhenSendRefreshToken_ShouldReturnTokenDto() {
    doReturn(false).when(jwtToken)
        .isTokenExpired(REFRESH_TOKEN);
    doReturn(USERNAME).when(jwtToken)
        .getUsernameFromToken(REFRESH_TOKEN);
    doReturn(TOKEN).when(jwtToken)
        .generateToken(USERNAME);
    doReturn(REFRESH_TOKEN).when(jwtToken)
        .generateRefreshToken(USERNAME);

    TokenDto result = authenticationService.refresh(REFRESH_TOKEN);

    assertEquals(TOKEN, result.getToken());
    assertEquals(REFRESH_TOKEN, result.getRefreshToken());
  }

  @Test
  void refresh_WhenSendNullRefreshToken_ShouldThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> authenticationService.refresh(null));
  }

  @Test
  void refresh_WhenSendExpiredRefreshToken_ShouldThrowTokenExpiredException() {
    doReturn(true).when(jwtToken)
        .isTokenExpired(REFRESH_TOKEN);

    assertThrows(TokenExpiredException.class, () -> authenticationService.refresh(REFRESH_TOKEN));
  }

  @Test
  void isTokenExpired_WhenSendNotExpiredRefreshToken_ShouldReturnFalse() {
    doReturn(false).when(jwtToken)
        .isTokenExpired(REFRESH_TOKEN);

    assertFalse(authenticationService.isTokenExpired(TOKEN));
  }

  @Test
  void isTokenExpired_WhenSendExpiredRefreshToken_ShouldReturnTrue() {
    doReturn(true).when(jwtToken)
        .isTokenExpired(TOKEN);

    assertTrue(authenticationService.isTokenExpired(TOKEN));
  }

  @Test
  void isTokenExpired_WhenSendNullRefreshToken_ShouldReturnTrue() {
    assertTrue(authenticationService.isTokenExpired(null));
  }

  @Test
  void isTokenExpired_WhenSendRefreshTokenButExceptionWasThrown_ShouldReturnTrue() {
    doThrow(RuntimeException.class).when(jwtToken)
        .isTokenExpired(TOKEN);

    assertTrue(authenticationService.isTokenExpired(TOKEN));
  }
}
