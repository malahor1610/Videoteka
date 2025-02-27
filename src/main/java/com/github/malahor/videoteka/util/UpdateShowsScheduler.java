package com.github.malahor.videoteka.util;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowDetails;
import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowLockState;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.repository.ShowRepository;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;

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
    var showsToUpdate = repository.findAll().stream().map(this::updateShow).filter(Optional::isPresent).map(Optional::get).toList();
    var partitions = ListUtils.partition(showsToUpdate, 100);
    partitions.forEach(repository::update);
    log.info("Finished updating the shows");
  }

  private Optional<ShowEntity> updateShow(ShowEntity show) {
    var details = apiService.details(show.getId(), show.getShowType());
    var poster = apiService.poster(show.getId(), show.getShowType());
    if (isApplicableForUpdate(show, details, poster)) {
      details.setTitle(details.getTitle() + " test");
      show.updateWithNewData(details, poster);
      log.info("Updating {} of user {}", show.getTitle(), show.getUserId());
      return Optional.of(show);
    }
    return Optional.empty();
  }

  private boolean isApplicableForUpdate(ShowEntity show, ShowDetails details, String poster) {
    return isTitleUpdated(show, details)
        || isOriginalTitleUpdated(show, details)
        || isReleaseDateUpdated(show, details)
        || isPosterUpdated(show, poster)
        || isDurationUpdated(show, details)
        || isLockStateUpdated(show, details)
        || isGenresUpdated(show, details);
  }

  private boolean isTitleUpdated(ShowEntity show, ShowDetails details) {
    return !show.getTitle().equals(details.getTitle());
  }

  private boolean isOriginalTitleUpdated(ShowEntity show, ShowDetails details) {
    return !show.getOriginalTitle().equals(details.getOriginalTitle());
  }

  private boolean isReleaseDateUpdated(ShowEntity show, ShowDetails details) {
    return !show.getReleaseDate().equals(details.getReleaseDate());
  }

  private boolean isPosterUpdated(ShowEntity show, String poster) {
    return !show.getPoster().equals(poster);
  }

  private boolean isDurationUpdated(ShowEntity show, ShowDetails details) {
    return !show.getDuration().equals(details.getDuration());
  }

  private boolean isLockStateUpdated(ShowEntity show, ShowDetails details) {
    if (ShowLockState.isLocked(show)) {
      return !ShowLockState.lockByDetails(details).equals(show.getLockState());
    }
    return false;
  }

  private boolean isGenresUpdated(ShowEntity show, ShowDetails details) {
    return !new HashSet<>(show.getGenres()).containsAll(details.getGenres())
        || !new HashSet<>(details.getGenres()).containsAll(show.getGenres());
  }
}
