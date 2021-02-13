package com.accenture.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class CommonConfigurationTest {

  private final CommonConfiguration configuration = new CommonConfiguration();

  @Test
  public void bCryptPasswordEncoder_ShouldReturnBCryptPasswordEncoderInstance() {
    assertThat(configuration.bCryptPasswordEncoder()).isInstanceOf(BCryptPasswordEncoder.class)
        .isNotNull();
  }

  @Test
  public void objectMapper_ShouldReturnObjectMapperInstance() {
    assertThat(configuration.objectMapper()).isInstanceOf(ObjectMapper.class)
        .isNotNull();
  }
}
