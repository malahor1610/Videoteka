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
public class UpdateSeriesStatus {

  private final ShowRepository repository;
  private final ApiService apiService;

  @Scheduled(cron = "0 33 3 * * ?")
  void cronJob() {
    log.info("Rozpoczęto aktualizację stanu seriali");
    updateStatuses(repository.findAll());
    log.info("Zakończono aktualizację stanu seriali");
  }

  private void updateStatuses(List<ShowEntity> shows) {
    shows.stream()
        .filter(show -> ShowType.SERIES.equals(show.getShowType()))
        .forEach(this::updateStatus);
  }

  private void updateStatus(ShowEntity show) {
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
      log.info("Zaktualizowano " + show.getTitle() + " użytkownika " + show.getUserId());
    }
  }
}
