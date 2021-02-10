package com.accenture.authentication.controller;

import com.accenture.authentication.service.AuthenticationService;
import com.accenture.pojo.UserCredentialsDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping(value = "authenticate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<String> authenticate(UserCredentialsDTO userCredentials) {
    String token = authenticationService.authenticate(userCredentials);
    return Strings.isNotBlank(token)
        ? ResponseEntity.ok(token)
        : ResponseEntity.badRequest()
            .build();
  }

  @PostMapping(value = "validate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<Boolean> isTokenExpired(String token) {
    return ResponseEntity.ok(authenticationService.isTokenExpired(token));
  }
}
