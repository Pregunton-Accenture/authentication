package com.accenture.authentication.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class UserDTOTest {

  final Class<?> classUnderTest = UserDTO.class;

  private final static String PASSWORD = "password";
  private final static String PASSWORD_2 = "password2";

  @Test
  void shouldPassAllPojoTests() {
    assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR).areWellImplemented();
  }

  @Test
  void arePasswordsEquals_WhenPasswordAndConfirmPasswordAreEquals_ShouldReturnTrue() {
    UserDTO userDTO = new UserDTO();
    userDTO.setPassword(PASSWORD);
    userDTO.setConfirmPassword(PASSWORD);

    Assertions.assertTrue(userDTO.arePasswordsEquals());
  }

  @Test
  void arePasswordsEquals_WhenPasswordAndConfirmPasswordAreNotEquals_ShouldReturnFalse() {
    UserDTO userDTO = new UserDTO();
    userDTO.setPassword(PASSWORD);
    userDTO.setConfirmPassword(PASSWORD_2);

    Assertions.assertFalse(userDTO.arePasswordsEquals());
  }

  @Test
  void arePasswordsEquals_WhenPasswordIsNull_ShouldReturnFalse() {
    UserDTO userDTO = new UserDTO();
    userDTO.setPassword(null);
    userDTO.setConfirmPassword(PASSWORD_2);

    Assertions.assertFalse(userDTO.arePasswordsEquals());
  }

  @Test
  void arePasswordsEquals_WhenConfirmPasswordIsNull_ShouldReturnFalse() {
    UserDTO userDTO = new UserDTO();
    userDTO.setPassword(PASSWORD);
    userDTO.setConfirmPassword(null);

    Assertions.assertFalse(userDTO.arePasswordsEquals());
  }

  @Test
  void arePasswordsEquals_WhenPasswordAndConfirmPasswordAreNull_ShouldReturnFalse() {
    UserDTO userDTO = new UserDTO();
    userDTO.setPassword(null);
    userDTO.setConfirmPassword(null);

    Assertions.assertFalse(userDTO.arePasswordsEquals());
  }
}
