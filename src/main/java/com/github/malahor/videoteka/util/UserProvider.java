package com.github.malahor.videoteka.util;

import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
@RequiredArgsConstructor
public class UserProvider {

  private final JsonWebToken jwt;

  public String getUsername() {
    return jwt.getClaim("cognito:username");
  }
}
