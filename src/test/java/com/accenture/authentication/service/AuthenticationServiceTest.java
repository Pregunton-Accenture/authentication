package com.accenture.authentication.service;

import com.accenture.authentication.jwt.JwtToken;
import com.accenture.model.UserCredentials;
import com.accenture.pojo.TokenDto;
import com.accenture.pojo.UserCredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
public class AuthenticationServiceTest {

  private static final String USERNAME = "user";
  private static final String PASSWORD = "pass";
  private static final String TOKEN = "token";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final UserCredentialsDTO CREDENTIALS_DTO = new UserCredentialsDTO(USERNAME, PASSWORD);
  private static final UserCredentials CREDENTIALS = new UserCredentials(USERNAME, PASSWORD);
  private static final TokenDto TOKEN_DTO = new TokenDto(TOKEN, REFRESH_TOKEN);

  @InjectMocks
  private AuthenticationService authenticationService;
  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private JwtUserDetailsService jwtUserDetailsService;
  @Mock
  private JwtToken jwtToken;
  @Mock
  private ObjectMapper objectMapper;

  @Test
  void authenticate_WhenUserCredentialsAreValid_ShouldReturnTokenDto() {
    doReturn(CREDENTIALS).when(objectMapper)
        .convertValue(CREDENTIALS_DTO, UserCredentials.class);
    doReturn(mock(Authentication.class)).when(authenticationManager)
        .authenticate(any());
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
  void authenticate_WhenUserCredentialsAreNotAuthenticated_ShouldThrowAuthenticationException() {
    doReturn(CREDENTIALS).when(objectMapper)
        .convertValue(CREDENTIALS_DTO, UserCredentials.class);
    doThrow(new AuthenticationCredentialsNotFoundException("message")).when(authenticationManager)
        .authenticate(any());

    assertThrows(AuthenticationException.class, () -> authenticationService.authenticate(CREDENTIALS_DTO));
  }
}
