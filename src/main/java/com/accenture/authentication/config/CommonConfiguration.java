package com.accenture.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CommonConfiguration {

  /**
   * Creates a BCryptPasswordEncoder bean.
   *
   * @return a BCryptPasswordEncoder instance
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Creates a ObjectMapper bean.
   *
   * @return an ObjectMapper instance
   */
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
