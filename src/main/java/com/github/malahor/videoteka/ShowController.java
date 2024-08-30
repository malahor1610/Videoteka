package com.github.malahor.videoteka;

import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.repository.ShowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
@CrossOrigin
public class ShowController {

  private final ShowRepository repository;

  @PutMapping("/positions")
  public ResponseEntity<List<ShowEntity>> updatePositions(@RequestBody List<ShowEntity> shows) {
    shows.forEach(show -> repository.updatePosition(show.getPosition(), show.getId()));
    var dbShows = (List<ShowEntity>) repository.findAll(Sort.by("position"));
    return ResponseEntity.ok(dbShows);
  }
}
