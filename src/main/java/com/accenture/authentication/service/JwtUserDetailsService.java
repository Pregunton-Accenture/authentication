package com.accenture.authentication.service;

import com.accenture.authentication.repository.UserCredentialsRepository;
import com.accenture.model.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserCredentialsRepository userCredentialsRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    if (Objects.nonNull(username)) {
      Optional<UserCredentials> optional = this.userCredentialsRepository.findByUsername(username);
      if (optional.isPresent()) {
        UserCredentials userCredentials = optional.get();
        return new User(userCredentials.getUsername(), userCredentials.getPassword(), new ArrayList<>());
      }
    }
    throw new UsernameNotFoundException("User not found with username: " + username);
  }
}
