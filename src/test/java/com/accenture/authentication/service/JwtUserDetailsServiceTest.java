package com.accenture.authentication.service;

import com.accenture.authentication.repository.UserCredentialsRepository;
import com.accenture.model.UserCredentials;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
public class JwtUserDetailsServiceTest {

  private static final String USERNAME = "username";
  private static final String PASSWORD = "pass";
  private static final ArrayList<? extends GrantedAuthority> AUTHORITIES = new ArrayList<>();
  private static final UserCredentials CREDENTIALS = new UserCredentials(USERNAME, PASSWORD);
  private static final UserDetails USER_DETAILS = new User(USERNAME, PASSWORD, AUTHORITIES);

  @InjectMocks
  private JwtUserDetailsService jwtUserDetailsService;
  @Mock
  private UserCredentialsRepository userCredentialsRepository;

  @Test
  public void loadUserByUsername_WhenUsernameExists_ShouldReturnUserDetails() {
    doReturn(Optional.of(CREDENTIALS)).when(userCredentialsRepository)
        .findByUsername(USERNAME);

    UserDetails result = jwtUserDetailsService.loadUserByUsername(USERNAME);

    assertEquals(USER_DETAILS.getUsername(), result.getUsername());
    assertEquals(USER_DETAILS.getPassword(), result.getPassword());
    assertEquals(USER_DETAILS.getAuthorities(), result.getAuthorities());
  }

  @Test
  public void loadUserByUsername_WhenUsernameNull_ShouldThrowUsernameNotFoundException() {
    assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername(USERNAME));
  }

  @Test
  public void loadUserByUsername_WhenUsernameDoesNotExists_ShouldThrowUsernameNotFoundException() {
    doReturn(Optional.empty()).when(userCredentialsRepository)
        .findByUsername(USERNAME);

    assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername(USERNAME));
  }
}
