package com.github.malahor.videoteka.the_movie_db;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;

@Component
public class ApiResolver {

  public String apiKey() {
    try {
      var path = Paths.get(getClass().getClassLoader().getResource("api.txt").toURI());
      return Files.readString(path);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public URI search(String title, SearchType type) {
    var query =
        String.format(api() + "/search/%s?query=%s&include_adult=true", type.getApiPath(), title);
    return URI.create(query);
  }

  private String api() {
    return "https://api.themoviedb.org/3";
  }
}
