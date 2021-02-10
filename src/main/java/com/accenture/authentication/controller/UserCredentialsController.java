package com.accenture.authentication.controller;

import com.accenture.authentication.service.UserCredentialsService;
import com.accenture.pojo.UserCredentialsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users")
public class UserCredentialsController {

  @Autowired
  private UserCredentialsService userCredentialsService;

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<Void> create(UserCredentialsDTO userCredentials) {
    userCredentialsService.insert(userCredentials);
    return ResponseEntity.ok()
        .build();
  }
}
