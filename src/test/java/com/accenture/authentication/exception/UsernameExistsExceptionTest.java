package com.accenture.authentication.exception;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class UsernameExistsExceptionTest {

  final Class<?> classUnderTest = UsernameExistsException.class;

  @Test
  void shouldPassAllPojoTests() {
    assertPojoMethodsFor(classUnderTest).testing(Method.CONSTRUCTOR).areWellImplemented();
  }
}


