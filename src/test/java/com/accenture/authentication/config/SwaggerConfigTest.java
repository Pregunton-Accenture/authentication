package com.accenture.authentication.config;

import org.junit.jupiter.api.Test;
import springfox.documentation.spring.web.plugins.Docket;

import static org.assertj.core.api.Assertions.assertThat;

public class SwaggerConfigTest {

  private final SwaggerConfig config = new SwaggerConfig();

  @Test
  public void api_ShouldReturnDocketInstance() {
    assertThat(config.api()).isInstanceOf(Docket.class)
        .isNotNull();
  }
}
