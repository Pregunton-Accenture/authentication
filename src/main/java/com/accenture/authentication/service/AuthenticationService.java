package com.accenture.authentication.service;

import com.accenture.authentication.exception.TokenExpiredException;
import com.accenture.authentication.jwt.JwtToken;
import com.accenture.model.UserCredentials;
import com.accenture.pojo.TokenDto;
import com.accenture.pojo.UserCredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

  @Autowired
  private JwtToken jwtToken;

  @Autowired
  private ObjectMapper objectMapper;

  public TokenDto authenticate(UserCredentialsDTO userCredentialsDTO) {
    Objects.requireNonNull(userCredentialsDTO);

    UserCredentials userCredentials = objectMapper.convertValue(userCredentialsDTO, UserCredentials.class);
    String username = userCredentials.getUsername();

    String token = jwtToken.generateToken(username);
    String refreshToken = jwtToken.generateRefreshToken(username);
    return new TokenDto(token, refreshToken);
  }

  public TokenDto refresh(String refreshToken) {
    Objects.requireNonNull(refreshToken);

    if (isTokenExpired(refreshToken)) {
      throw new TokenExpiredException();
    }
    String username = jwtToken.getUsernameFromToken(refreshToken);
    String newToken = jwtToken.generateToken(username);
    String newRefreshToken = jwtToken.generateRefreshToken(username);
    return new TokenDto(newToken, newRefreshToken);
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
