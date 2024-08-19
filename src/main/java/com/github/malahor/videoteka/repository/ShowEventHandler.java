package com.github.malahor.videoteka.repository;

import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.exception.ShowPresentOnWatchlistException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class ShowEventHandler {

  private final ShowRepository repository;

  @HandleBeforeCreate
  public void handlePosition(ShowEntity v) throws ShowPresentOnWatchlistException {
    var show = repository.findById(v.getId());
    if (show.isPresent()) throw new ShowPresentOnWatchlistException();
    var maxPosition = repository.findMaxPosition().orElse(0);
    maxPosition++;
    v.setPosition(maxPosition);
  }
}
