package com.github.malahor.videoteka.api.tmdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowType;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "api.used", havingValue = "tmdb")
public class TMDbService implements ApiService {

  @Value("${api.key}")
  private String apiKey;

  private final TMDbUriResolver uriResolver;
  private final ObjectMapper mapper;

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
