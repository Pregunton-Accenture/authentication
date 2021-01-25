package com.accenture.authentication.controller;

import com.accenture.authentication.dto.UserDTO;
import com.accenture.authentication.exception.PasswordsNotEqualsException;
import com.accenture.authentication.exception.UsernameExistsException;
import com.accenture.authentication.pojo.User;
import com.accenture.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> register(@RequestBody UserDTO userDTO) throws PasswordsNotEqualsException,
      UsernameExistsException {
    Optional<User> result = userService.save(userDTO);
    return result.map(user -> ResponseEntity.ok(user.getUsername())).orElseGet(() -> ResponseEntity.badRequest().build());
  }
}
