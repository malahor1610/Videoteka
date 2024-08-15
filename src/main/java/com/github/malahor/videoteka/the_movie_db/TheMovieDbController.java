package com.github.malahor.videoteka.the_movie_db;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class TheMovieDbController {

  private final ApiResolver apiResolver;
  private final ObjectMapper mapper;

  @GetMapping
  public ResponseEntity<SearchResult> searchFor(
      @RequestParam("title") String title, @RequestParam("type") SearchType type) {
    var request =
        HttpRequest.newBuilder()
            .uri(apiResolver.search(title, type))
            .header("accept", "application/json")
            .header("Authorization", "Bearer " + apiResolver.apiKey())
            .GET()
            .build();

    try (var client = HttpClient.newHttpClient()) {
      var response = client.send(request, HttpResponse.BodyHandlers.ofString());
      var result = mapper.readValue(response.body(), SearchResult.class);
      result.getResults().sort(Comparator.comparing(Show::getPopularity).reversed());
      return ResponseEntity.ok(result);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
