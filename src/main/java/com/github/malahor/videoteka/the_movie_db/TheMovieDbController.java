package com.github.malahor.videoteka.the_movie_db;

import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class TheMovieDbController {

  private final ApiResolver apiResolver;
  private final TheMovieDbService service;

  @GetMapping
  public ResponseEntity<SearchResult> searchFor(
      @RequestParam("title") String title, @RequestParam("type") SearchType type) {
    var result =
        service.get(apiResolver.search(title, type), apiResolver.apiKey(), SearchResult.class);
    result.getResults().sort(Comparator.comparing(Show::getPopularity).reversed());
    result.getResults().forEach(show -> show.setType(type));
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ShowDetails> searchDetails(
      @PathVariable("id") long id, @RequestParam("type") SearchType type) {
    var result =
        service.get(apiResolver.details(id, type), apiResolver.apiKey(), ShowDetails.class);
    result.setType(type);
    return ResponseEntity.ok(result);
  }
}
