package com.github.malahor.videoteka;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.api.DomainMapper;
import com.github.malahor.videoteka.domain.Show;
import com.github.malahor.videoteka.domain.ShowDetails;
import com.github.malahor.videoteka.domain.ShowType;
import java.util.List;

import com.github.malahor.videoteka.exception.ShowsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

  private final ApiService service;
  private final DomainMapper mapper;

  @GetMapping
  public ResponseEntity<List<Show>> searchFor(
      @RequestParam("title") String title, @RequestParam("type") ShowType type) {
    var result = service.search(title, type);
    var shows = mapper.mapSearchResult(result, type);
    if (shows.isEmpty()) throw new ShowsNotFoundException();
    return ResponseEntity.ok(shows);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ShowDetails> searchDetails(
      @PathVariable("id") long id, @RequestParam("type") ShowType type) {
    var result = service.details(id, type);
    var details = mapper.mapSearchDetails(result, type);
    return ResponseEntity.ok(details);
  }
}
