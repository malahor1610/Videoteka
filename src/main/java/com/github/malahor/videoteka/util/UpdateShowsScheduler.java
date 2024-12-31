package com.github.malahor.videoteka.util;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowLockState;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.repository.ShowRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class UpdateShowsScheduler {

  private final ShowRepository repository;
  private final ApiService apiService;

  @MemoryLog
  @Scheduled(cron = "0 33 3 * * ?")
  void cronJob() {
    log.info("Started updating the shows");
    updateShows(repository.findAll());
    log.info("Finished updating the shows");
  }

  private void updateShows(List<ShowEntity> shows) {
    shows.stream()
        .filter(show -> ShowType.SERIES.equals(show.getShowType()))
        .forEach(this::updateShow);
  }

  private void updateShow(ShowEntity show) {
    var applicableForUpdate = false;
    var details = apiService.details(show.getId(), show.getShowType());
    if (!show.getDuration().equals(details.getDuration())) {
      show.setDuration(details.getDuration());
      applicableForUpdate = true;
    }
    if (ShowLockState.isLocked(show)) {
      var newStatus = ShowLockState.lockByDetails(details);
      if (!newStatus.equals(show.getLockState())) {
        show.setLockState(newStatus.changed());
        applicableForUpdate = true;
      }
    }
    if (applicableForUpdate) {
      repository.update(show);
      log.info("Updated " + show.getTitle() + " of user " + show.getUserId());
    }
  }
}
