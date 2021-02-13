package com.accenture.authentication.controller;

import com.accenture.authentication.exception.UsernameExistException;
import com.accenture.authentication.service.UserCredentialsService;
import com.accenture.model.UserCredentials;
import com.accenture.pojo.UserCredentialsDTO;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserCredentialsControllerTest {

  private static final String USERNAME = "user";
  private static final String PASSWORD = "pass";
  private static final UserCredentials CREDENTIALS = new UserCredentials(USERNAME, PASSWORD);

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserCredentialsController userCredentialsController;
  @MockBean
  private UserCredentialsService userCredentialsService;

  @Test
  public void create_WhenSendUserCredentials_ShouldReturnOK() throws Exception {
    doReturn(CREDENTIALS).when(userCredentialsService)
        .insert(any());

    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content("username=user&password=pass"))
        .andExpect(status().isOk());
  }

  @Test
  public void create_WhenSendNullUserCredentials_ShouldReturnBadRequest() throws Exception {
    doThrow(NullPointerException.class).when(userCredentialsService)
        .insert(any());

    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void create_WhenSendAlreadyExistsUserCredentials_ShouldReturnUnauthorized() throws Exception {
    doThrow(UsernameExistException.class).when(userCredentialsService)
        .insert(any());

    mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content("username=user&password=pass"))
        .andExpect(status().isUnauthorized());
  }
}
