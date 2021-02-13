package com.accenture.authentication.controller;

import com.accenture.authentication.exception.TokenExpiredException;
import com.accenture.authentication.service.AuthenticationService;
import com.accenture.model.UserCredentials;
import com.accenture.pojo.TokenDto;
import com.accenture.pojo.UserCredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

  private static final String USERNAME = "user";
  private static final String PASSWORD = "pass";
  private static final String TOKEN = "token";
  private static final String REFRESH_TOKEN = "refresh_token";
  private static final UserCredentialsDTO CREDENTIALS_DTO = new UserCredentialsDTO(USERNAME, PASSWORD);
  private static final UserCredentials CREDENTIALS = new UserCredentials(USERNAME, PASSWORD);
  private static final TokenDto TOKEN_DTO = new TokenDto(TOKEN, REFRESH_TOKEN);

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private AuthenticationController authenticationController;
  @MockBean
  private AuthenticationService authenticationService;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void authenticate_WhenSendUserCredentials_ShouldReturnTokenDto() throws Exception {
    doReturn(TOKEN_DTO).when(authenticationService)
        .authenticate(any());

    mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content("username=user&password=pass"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(TOKEN_DTO)));
  }

  @Test
  public void authenticate_WhenSendNullUserCredentials_ShouldReturnBadRequest() throws Exception {
    doThrow(NullPointerException.class).when(authenticationService)
        .authenticate(any());

    mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void authenticate_WhenSendUserCredentialsAndUnhandledExceptionIsThrown_ShouldReturnInternalServerError()
      throws Exception {
    doThrow(RuntimeException.class).when(authenticationService)
        .authenticate(any());

    mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void refresh_WhenSendToken_ShouldReturnTokenDto() throws Exception {
    doReturn(TOKEN_DTO).when(authenticationService)
        .refresh(any());

    mockMvc.perform(post("/refresh").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content("refresh_token=refresh_token"))
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(TOKEN_DTO)));
  }

  @Test
  public void refresh_WhenSendNullToken_ShouldReturnBadRequest() throws Exception {
    doThrow(NullPointerException.class).when(authenticationService)
        .refresh(any());

    mockMvc.perform(post("/refresh").contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void refresh_WhenSendExpiredToken_ShouldReturnUnauthorized() throws Exception {
    doThrow(TokenExpiredException.class).when(authenticationService)
        .refresh(any());

    mockMvc.perform(post("/refresh").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content("refresh_token=refresh_token"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void isTokenExpired_WhenSendNotExpiredToken_ShouldReturnFalse() throws Exception {
    doReturn(false).when(authenticationService)
        .isTokenExpired(any());

    mockMvc.perform(post("/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content("refresh_token=refresh_token"))
        .andExpect(status().isOk())
        .andExpect(content().string("false"));
  }

  @Test
  public void isTokenExpired_WhenSendExpiredToken_ShouldReturnTrue() throws Exception {
    doReturn(true).when(authenticationService)
        .isTokenExpired(any());

    mockMvc.perform(post("/validate").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content("refresh_token=refresh_token"))
        .andExpect(status().isOk())
        .andExpect(content().string("true"));
  }
}
