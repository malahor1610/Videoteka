package com.github.malahor.videoteka.repository;

import com.github.malahor.videoteka.domain.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
@RequiredArgsConstructor
public class VideoEventHandler {

  private final VideoRepository repository;
  @HandleBeforeCreate
  public void handlePosition(Video v) {
    var maxPosition = repository.findMaxPosition();
    maxPosition++;
    v.setPosition(maxPosition);
  }
}
