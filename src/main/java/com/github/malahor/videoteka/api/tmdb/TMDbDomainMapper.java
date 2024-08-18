package com.github.malahor.videoteka.api.tmdb;

import com.github.malahor.videoteka.api.DomainMapper;
import com.github.malahor.videoteka.api.SearchDetails;
import com.github.malahor.videoteka.api.SearchResult;
import com.github.malahor.videoteka.domain.Show;
import com.github.malahor.videoteka.domain.ShowDetails;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.domain.WatchProviders;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "api.used", havingValue = "tmdb")
public class TMDbDomainMapper implements DomainMapper {

  private static final String POSTER_URI = "http://image.tmdb.org/t/p/w185";

  @Override
  public List<Show> mapSearchResult(SearchResult searchResult, ShowType type) {
    if (searchResult instanceof TMDbSearchResult results)
      return results.getResults().stream()
          .map(result -> mapToShow(result, type))
          .sorted(Comparator.comparing(Show::getPopularity).reversed())
          .toList();
    return Collections.emptyList();
  }

  private Show mapToShow(TMDbSearchResultShow result, ShowType type) {
    var show = new Show();
    show.setId(result.getId());
    show.setTitle(result.getTitle());
    show.setReleaseDate(yearOf(result.getReleaseDate()));
    show.setPoster(POSTER_URI + result.getPoster());
    show.setPopularity(result.getPopularity());
    show.setType(type);
    return show;
  }

  @Override
  public ShowDetails mapSearchDetails(SearchDetails searchDetails, ShowType type) {
    if (searchDetails instanceof TMDbSearchDetails details) {
      var showDetails = new ShowDetails();
      showDetails.setId(details.getId());
      showDetails.setTitle(details.getTitle());
      showDetails.setOriginalTitle(details.getOriginalTitle());
      showDetails.setOverview(details.getOverview());
      showDetails.setReleaseDate(yearOf(details.getReleaseDate()));
      showDetails.setDuration(durationOf(details.getRuntime(), type));
      showDetails.setGenres(details.getGenres());
      showDetails.setWatchProviders(mapWatchProviders(details.getWatchProviders()));
      showDetails.setType(type);
      return showDetails;
    }
    return null;
  }

  private WatchProviders mapWatchProviders(TMDbWatchProviders tmdbWatchProviders) {
    if (tmdbWatchProviders == null) return WatchProviders.noProviders();
    var watchProviders = new WatchProviders();
    watchProviders.setAvailable(tmdbWatchProviders.getFlatrate());
    watchProviders.setRent(tmdbWatchProviders.getRent());
    watchProviders.setBuy(tmdbWatchProviders.getBuy());
    return watchProviders;
  }

  private String durationOf(int duration, ShowType type) {
    return duration
        + " "
        + switch (type) {
          case MOVIE -> "minutes";
          case SERIES -> "episodes";
        };
  }

  private String yearOf(String releaseDate) {
    if (releaseDate == null || releaseDate.isEmpty()) return "";
    else return String.valueOf(LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE).getYear());
  }
}
