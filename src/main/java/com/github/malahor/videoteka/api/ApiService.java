package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.malahor.videoteka.domain.ShowType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ApiService {

  @Inject private UriResolver uriResolver;

  @Inject private ObjectMapper mapper;

  @Inject
  @ConfigProperty(name = "api.key")
  private String apiKey;

  public SearchResult search(String title, ShowType type) {
    return this.get(uriResolver.search(title, type), SearchResult.class);
  }

  public SearchDetails details(long id, ShowType type) {
    return this.get(uriResolver.details(id, type), SearchDetails.class);
  }

  public SearchCollectionDetails collection(long id) {
    return this.get(uriResolver.collection(id), SearchCollectionDetails.class);
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
