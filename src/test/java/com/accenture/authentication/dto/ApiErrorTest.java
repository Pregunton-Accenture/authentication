package com.accenture.authentication.dto;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class ApiErrorTest {

  final Class<?> classUnderTest = ApiError.class;

  @Test
  void shouldPassAllPojoTests() {
    assertPojoMethodsFor(classUnderTest).testing(Method.CONSTRUCTOR).areWellImplemented();
  }
}
