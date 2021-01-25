package com.accenture.authentication.service;

import com.accenture.authentication.dto.UserDTO;
import com.accenture.authentication.exception.PasswordsNotEqualsException;
import com.accenture.authentication.exception.UsernameExistsException;
import com.accenture.authentication.pojo.User;
import com.accenture.authentication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

  private final static UserDTO USER_DTO = new UserDTO();
  private final static User USER = new User();
  private final static String USERNAME = "username";
  private final static String PASSWORD = "password";
  private final static String CONFIRM_PASSWORD = "password";
  private final static String DIFFERENT_PASSWORD = "different";
  private final static String ENCODED_PASSWORD = "$2a$10$E3.7C33QNKXNgAh174XO8uvMP2YJ/rDW.52pV9ZIv9F7EeuVVLi/y";
  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private BCryptPasswordEncoder passwordEncoder;

  @BeforeEach
  public void setUp() {
    USER_DTO.setUsername(USERNAME);
    USER_DTO.setPassword(PASSWORD);
    USER_DTO.setConfirmPassword(CONFIRM_PASSWORD);

    USER.setUsername(USERNAME);
    USER.setPassword(ENCODED_PASSWORD);
  }

  @Test
  void save_WhenValidUserDTOData_ShouldReturnOptionalWithUserInstance() throws PasswordsNotEqualsException,
      UsernameExistsException {
    doReturn(false).when(userRepository).existsByUsername(eq(USERNAME));
    doReturn(ENCODED_PASSWORD).when(passwordEncoder).encode(eq(PASSWORD));
    doReturn(USER).when(userRepository).save(eq(USER));

    Optional<User> result = userService.save(USER_DTO);

    assertTrue(result.isPresent());
    assertEquals(result.get(), USER);
  }

  @Test
  void save_WhenPasswordsAreNotEquals_ShouldThrowPasswordsNotEqualsException() {
    USER_DTO.setPassword(DIFFERENT_PASSWORD);

    assertThrows(PasswordsNotEqualsException.class, () -> userService.save(USER_DTO));
  }

  @Test
  void save_WhenUsernameAlreadyExists_ShouldThrowUsernameExistsException() {
    doReturn(true).when(userRepository).existsByUsername(eq(USERNAME));

    assertThrows(UsernameExistsException.class, () -> userService.save(USER_DTO));
  }

  @Test
  void save_WhenValidUserDTOData_ShouldReturnEmptyOptional() throws PasswordsNotEqualsException,
      UsernameExistsException {
    doReturn(false).when(userRepository).existsByUsername(eq(USERNAME));
    doReturn(ENCODED_PASSWORD).when(passwordEncoder).encode(eq(PASSWORD));
    doReturn(null).when(userRepository).save(eq(USER));

    Optional<User> result = userService.save(USER_DTO);

    assertFalse(result.isPresent());
  }
}
