package com.github.malahor.videoteka.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyResolver {

  public String apiKey() {
    try {
      var path = Paths.get(getClass().getClassLoader().getResource("api.txt").toURI());
      return Files.readString(path);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
