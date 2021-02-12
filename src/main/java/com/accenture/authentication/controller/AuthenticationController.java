package com.accenture.authentication.controller;

import com.accenture.authentication.service.AuthenticationService;
import com.accenture.pojo.TokenDto;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
@Api(tags = "Authentication API", produces = MediaType.APPLICATION_JSON_VALUE,
     consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public class AuthenticationController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping(value = "authenticate")
  @ApiOperation("Retrieves a token and refresh_token of the provided credentials.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 401, message = "Unauthorized"),
      @ApiResponse(code = 500, message = "Internal Server Error"),
  })
  @ResponseBody
  public ResponseEntity<TokenDto> authenticate(UserCredentialsDTO userCredentials) {
    TokenDto tokenDto = authenticationService.authenticate(userCredentials);
    return ResponseEntity.ok(tokenDto);
  }

  @PostMapping(value = "refresh")
  @ApiOperation("Generates a new set of token and refresh_token with the refresh_token provided.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok"),
      @ApiResponse(code = 400, message = "Bad Request"),
      @ApiResponse(code = 401, message = "Unauthorized"),
      @ApiResponse(code = 500, message = "Internal Server Error"),
  })
  @ResponseBody
  public ResponseEntity<TokenDto> refresh(String refreshToken) {
    TokenDto tokenDto = authenticationService.refresh(refreshToken);
    return ResponseEntity.ok(tokenDto);
  }

  @PostMapping(value = "validate")
  @ApiOperation("Validates if the provided token is expired")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Ok")
  })
  public ResponseEntity<Boolean> isTokenExpired(String token) {
    return ResponseEntity.ok(authenticationService.isTokenExpired(token));
  }
}
