package com.github.malahor.videoteka.the_movie_db;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TheMovieDbService {

  private final ObjectMapper mapper;

  public <T> T get(URI uri, String apiKey, Class<T> resultType) {
    var request =
        HttpRequest.newBuilder()
            .uri(uri)
            .header("accept", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .GET()
            .build();

    try (var client = HttpClient.newHttpClient()) {
      var response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return mapper.readValue(response.body(), resultType);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
