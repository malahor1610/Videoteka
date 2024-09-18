package com.github.malahor.videoteka;

import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.exception.ShowPresentOnWatchlistException;
import com.github.malahor.videoteka.repository.ShowRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
    var shows = repository.findAll(username);
    if (shows.stream().map(ShowEntity::getId).anyMatch(s -> s.equals(show.getId())))
      throw new ShowPresentOnWatchlistException();
    var maxPosition = shows.isEmpty() ? 0 : shows.getLast().getPosition();
    maxPosition++;
    show.setPosition(maxPosition);
    show.setUserId(username);
    repository.save(show);
    return ResponseEntity.ok(show);
  }

  @PutMapping("/positions")
  public ResponseEntity<List<ShowEntity>> updatePositions(
      @AuthenticationPrincipal Jwt jwt, @RequestBody List<ShowEntity> shows) {
    var username = getUserId(jwt);
    var dbShows = repository.findAll(username);
    dbShows.forEach(dbShow -> updatePosition(dbShow, shows));
    dbShows.forEach(repository::update);
    dbShows.sort(Comparator.comparingInt(ShowEntity::getPosition));
    return ResponseEntity.ok(dbShows);
  }

  private void updatePosition(ShowEntity dbShow, List<ShowEntity> shows) {
    shows.stream()
        .filter(show -> show.getId() == dbShow.getId())
        .findFirst()
        .ifPresent(show -> dbShow.setPosition(show.getPosition()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
    var username = getUserId(jwt);
    repository.delete(id, username);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<ShowEntity>> getAll(@AuthenticationPrincipal Jwt jwt) {
    var username = getUserId(jwt);
    var dbShows = (List<ShowEntity>) repository.findAll(username);
    return ResponseEntity.ok(dbShows);
  }

  @GetMapping("/type/{type}")
  public ResponseEntity<List<ShowEntity>> getByType(
      @AuthenticationPrincipal Jwt jwt, @PathVariable ShowType type) {
    var username = getUserId(jwt);
    var dbShows = (List<ShowEntity>) repository.findByType(type, username);
    return ResponseEntity.ok(dbShows);
  }

  private String getUserId(Jwt jwt) {
    return Optional.ofNullable(jwt.getClaimAsString("cognito:username"))
        .orElseGet(() -> jwt.getClaimAsString("email"));
  }
}
