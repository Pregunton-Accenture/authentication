package com.accenture.authentication.service;

import com.accenture.authentication.dto.UserDTO;
import com.accenture.authentication.exception.PasswordsNotEqualsException;
import com.accenture.authentication.exception.UsernameExistsException;
import com.accenture.authentication.pojo.User;
import com.accenture.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  /**
   * Saves a new user into database.
   *
   * @param userDTO user credentials to be stored.
   *
   * @return an empty {@link Optional} if  user was not saved; otherwise an {@link Optional} with the user credentials.
   *
   * @throws PasswordsNotEqualsException if passwords are not equals.
   * @throws UsernameExistsException if username already exists.
   */
  public Optional<User> save(UserDTO userDTO) throws PasswordsNotEqualsException, UsernameExistsException {
    if (!userDTO.arePasswordsEquals()) {
      throw new PasswordsNotEqualsException();
    }
    String username = userDTO.getUsername();
    if (userRepository.existsByUsername(username)) {
      throw new UsernameExistsException(username);
    }
    User user = new User(username, passwordEncoder.encode(userDTO.getPassword()));
    return Optional.of(userRepository.save(user));
  }

}
