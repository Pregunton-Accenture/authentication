package com.accenture.authentication.controller;

import com.accenture.authentication.service.UserCredentialsService;
import com.accenture.pojo.UserCredentialsDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "users")
@Api(tags = "User Credentials API")
public class UserCredentialsController {

  @Autowired
  private UserCredentialsService userCredentialsService;

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ApiOperation("Saves the new user credentials.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 500, message = "Internal Server Error"),
  })
  public ResponseEntity<Void> create(UserCredentialsDTO userCredentials) {
    userCredentialsService.insert(userCredentials);
    return ResponseEntity.ok()
        .build();
  }
}
