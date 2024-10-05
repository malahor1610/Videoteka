package com.github.malahor.videoteka.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.malahor.videoteka.domain.Show;
import com.github.malahor.videoteka.domain.ShowCollectionDetails;
import com.github.malahor.videoteka.domain.ShowDetails;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.exception.ShowsNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@RequiredArgsConstructor
public class ApiService {

  private final UriResolver uriResolver;
  private final ObjectMapper mapper;
  private final DomainMapper domainMapper;

  @Inject
  @ConfigProperty(name = "api.key")
  private String apiKey;

  public List<Show> search(String title, ShowType type) {
    var result = this.get(uriResolver.search(title, type), SearchResult.class);
    return domainMapper.mapSearchResult(result, type);
  }

  public String poster(long id, ShowType type) {
    var result = this.get(uriResolver.poster(id, type), SearchPoster.class);
    return domainMapper.mapSearchPoster(result);
  }

  public ShowDetails details(long id, ShowType type) {
    var result = this.get(uriResolver.details(id, type), SearchDetails.class);
    return domainMapper.mapSearchDetails(result, type);
  }

  public ShowCollectionDetails collection(long id) {
    var result = this.get(uriResolver.collection(id), SearchCollectionDetails.class);
    return domainMapper.mapSearchCollectionDetails(result);
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
      throw new ShowsNotFoundException();
    }
  }
}
