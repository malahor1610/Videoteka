package com.github.malahor.videoteka.db_integrity;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.repository.ShowRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class DbIntegrityHandler {

  private final ShowRepository repository;
  private final ApiService apiService;

  void onStart(@Observes StartupEvent ev) {
    log.info("Verifying database integrity");
    var shows = repository.findAll();
    updateGenres(shows);
    log.info("Finished verifying database integrity");
  }

  void updateGenres(List<ShowEntity> shows) {
    var toUpdate =
        shows.stream()
            .filter(show -> show.getGenres() == null || show.getGenres().isEmpty())
            .toList();
    toUpdate.forEach(
        show -> {
          var details = apiService.details(show.getId(), show.getShowType());
          show.setGenres(details.getGenres());
        });
    var partitions = ListUtils.partition(toUpdate, 100);
    partitions.forEach(repository::update);
  }
}
