package com.github.malahor.videoteka;

import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.exception.ShowPresentOnWatchlistException;
import com.github.malahor.videoteka.repository.ShowRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

  private final ShowRepository repository;

  @PostMapping
  public ResponseEntity<ShowEntity> save(
      @AuthenticationPrincipal Jwt jwt, @RequestBody ShowEntity show) {
    var username = getUserId(jwt);
    var dbShow = repository.findById(show.getId());
    if (dbShow.isPresent()) throw new ShowPresentOnWatchlistException();
    var maxPosition = repository.findMaxPosition().orElse(0);
    maxPosition++;
    show.setPosition(maxPosition);
    var result = repository.save(show);
    return ResponseEntity.ok(result);
  }

  @PutMapping("/positions")
  public ResponseEntity<List<ShowEntity>> updatePositions(
      @AuthenticationPrincipal Jwt jwt, @RequestBody List<ShowEntity> shows) {
    var username = getUserId(jwt);
    shows.forEach(show -> repository.updatePosition(show.getPosition(), show.getId()));
    var dbShows = (List<ShowEntity>) repository.findAll(Sort.by("position"));
    return ResponseEntity.ok(dbShows);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
    var username = getUserId(jwt);
    repository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<ShowEntity>> getAll(@AuthenticationPrincipal Jwt jwt) {
    var username = getUserId(jwt);
    var dbShows = (List<ShowEntity>) repository.findAll(Sort.by("position"));
    return ResponseEntity.ok(dbShows);
  }

  @GetMapping("/type/{type}")
  public ResponseEntity<List<ShowEntity>> getByType(
      @AuthenticationPrincipal Jwt jwt, @PathVariable ShowType type) {
    var username = getUserId(jwt);
    var dbShows = (List<ShowEntity>) repository.findByShowTypeOrderByPosition(type);
    return ResponseEntity.ok(dbShows);
  }

  private String getUserId(Jwt jwt) {
    return Optional.ofNullable(jwt.getClaimAsString("cognito:username"))
        .orElseGet(() -> jwt.getClaimAsString("email"));
  }
}
