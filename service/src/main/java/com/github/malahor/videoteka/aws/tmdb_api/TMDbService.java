package com.github.malahor.videoteka.aws.tmdb_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.malahor.videoteka.aws.domain.ShowType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TMDbService {

  private final TMDbUriResolver uriResolver;
  private final ObjectMapper mapper;
  private final String apiKey;

  public TMDbService(String apiKey) {
    this.uriResolver = new TMDbUriResolver();
    this.mapper = new ObjectMapper();
    this.apiKey = apiKey;
  }

  public TMDbSearchResult search(String title, ShowType type) {
    return this.get(uriResolver.search(title, type), TMDbSearchResult.class);
  }

  public TMDbSearchDetails details(long id, ShowType type) {
    return this.get(uriResolver.details(id, type), TMDbSearchDetails.class);
  }

  private <T> T get(URI uri, Class<T> resultType) {
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
