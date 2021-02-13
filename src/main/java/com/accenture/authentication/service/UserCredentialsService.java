package com.accenture.authentication.service;

import com.accenture.authentication.exception.UsernameExistException;
import com.accenture.authentication.repository.UserCredentialsRepository;
import com.accenture.model.UserCredentials;
import com.accenture.pojo.UserCredentialsDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserCredentialsService {

  @Autowired
  private UserCredentialsRepository userCredentialsRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private ObjectMapper objectMapper;

  public UserCredentials insert(UserCredentialsDTO userCredentialsDTO) {
    Objects.requireNonNull(userCredentialsDTO);

    UserCredentials userCredentials = objectMapper.convertValue(userCredentialsDTO, UserCredentials.class);
    if (userCredentialsRepository.findByUsername(userCredentials.getUsername())
        .isPresent()) {
      throw new UsernameExistException(userCredentials.getUsername());
    }

    String encodedPassword = passwordEncoder.encode(userCredentials.getPassword());
    userCredentials.setPassword(encodedPassword);
    return userCredentialsRepository.save(userCredentials);
  }
}
