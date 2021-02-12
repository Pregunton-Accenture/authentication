package com.accenture.authentication.service;

import com.accenture.authentication.exception.UsernameExistException;
import com.accenture.authentication.exception.UsernameExistExceptionTest;
import com.accenture.authentication.repository.UserCredentialsRepository;
import com.accenture.model.UserCredentials;
import com.accenture.pojo.UserCredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
public class UserCredentialsServiceTest {

  private static final String USERNAME = "user";
  private static final String PASSWORD = "encoded_password";
  private static final UserCredentialsDTO CREDENTIALS_DTO = new UserCredentialsDTO(USERNAME, PASSWORD);
  private static final UserCredentials CREDENTIALS = new UserCredentials(USERNAME, PASSWORD);

  @InjectMocks
  private UserCredentialsService userCredentialsService;
  @Mock
  private UserCredentialsRepository userCredentialsRepository;
  @Mock
  private BCryptPasswordEncoder passwordEncoder;
  @Mock
  private ObjectMapper objectMapper;

  @Test
  public void insert_WhenCredentialsAreValid_ShouldReturnTheUserCredentials() {
    String encodedPassword = "encoded_password";
    doReturn(CREDENTIALS).when(objectMapper)
        .convertValue(CREDENTIALS_DTO, UserCredentials.class);
    doReturn(Optional.empty()).when(userCredentialsRepository)
        .findByUsername(CREDENTIALS.getUsername());
    doReturn(encodedPassword).when(passwordEncoder)
        .encode(CREDENTIALS.getPassword());
    doReturn(CREDENTIALS).when(userCredentialsRepository)
        .save(any());

    UserCredentials result = userCredentialsService.insert(CREDENTIALS_DTO);

    assertEquals(CREDENTIALS.getUsername(), result.getUsername());
    assertEquals(encodedPassword, result.getPassword());
  }

  @Test
  public void insert_WhenCredentialsAreNull_ShouldThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> userCredentialsService.insert(null));
  }

  @Test
  public void insert_WhenCredentialsAreValidAndUsernameAlreadyExists_ShouldThrowUsernameExistException() {
    doReturn(CREDENTIALS).when(objectMapper)
        .convertValue(CREDENTIALS_DTO, UserCredentials.class);
    doReturn(Optional.of(CREDENTIALS)).when(userCredentialsRepository)
        .findByUsername(CREDENTIALS.getUsername());

    assertThrows(UsernameExistException.class, () -> userCredentialsService.insert(CREDENTIALS_DTO));
  }
}
