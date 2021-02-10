package com.accenture.authentication.service;

import com.accenture.authentication.jwt.JwtToken;
import com.accenture.model.UserCredentials;
import com.accenture.pojo.UserCredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;

  @Autowired
  private JwtToken jwtToken;

  @Autowired
  private ObjectMapper objectMapper;

  public String authenticate(UserCredentialsDTO userCredentialsDTO) {
    Objects.requireNonNull(userCredentialsDTO);

    UserCredentials userCredentials = objectMapper.convertValue(userCredentialsDTO, UserCredentials.class);
    String username = userCredentials.getUsername();

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, userCredentials.getPassword()));
    jwtUserDetailsService.loadUserByUsername(username);
    return jwtToken.generateToken(username);
  }

  public boolean isTokenExpired(String token) {
    boolean result = true;
    if (Objects.nonNull(token)) {
      try {
        result = jwtToken.isTokenExpired(token);
      } catch (Exception ignored) {
      }
    }
    return result;
  }
}
