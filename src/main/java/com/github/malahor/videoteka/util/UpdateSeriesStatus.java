package com.github.malahor.videoteka.util;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowStatus;
import com.github.malahor.videoteka.repository.ShowRepository;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class UpdateSeriesStatus {

  @Inject private ShowRepository repository;

  @Inject private ApiService apiService;

  @Scheduled(cron = "0 33 3 * * ?")
  void cronJob(ScheduledExecution execution) {
    log.info(execution.getScheduledFireTime().toString());
    updateStatuses(repository.findLocked());
  }

  private void updateStatuses(List<ShowEntity> shows) {
    var futures =
        shows.stream()
            .filter(ShowStatus::isLocked)
            .map(show -> Thread.ofVirtual().start(() -> updateStatus(show)))
            .toList();
    try {
      for (var future : futures) future.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void updateStatus(ShowEntity show) {
    log.info("Start updateStatus show " + show.getTitle());
    var details = apiService.details(show.getId(), show.getShowType());
    var newStatus = ShowStatus.lockByDetails(details);
    if (!newStatus.equals(show.getShowStatus())) {
      show.setShowStatus(newStatus.changed());
      repository.update(show);
    }
    log.info("End updateStatus show " + show.getTitle());
  }
}
