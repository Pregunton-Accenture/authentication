package com.accenture.authentication.pojo;

import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

class UserTest {

  final Class<?> classUnderTest = User.class;

  @Test
  void shouldPassAllPojoTests() {
    assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR).areWellImplemented();
  }
}
