package com.github.malahor.videoteka.aws.tmdb_api;

import com.github.malahor.videoteka.aws.domain.Show;
import com.github.malahor.videoteka.aws.domain.ShowDetails;
import com.github.malahor.videoteka.aws.domain.ShowType;
import com.github.malahor.videoteka.aws.domain.WatchProviders;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class TMDbDomainMapper {

  private static final String POSTER_URI = "http://image.tmdb.org/t/p/w342";

  public List<Show> mapSearchResult(TMDbSearchResult results, ShowType type) {
    return results.getResults().stream()
        .map(result -> mapToShow(result, type))
        .sorted(Comparator.comparing(Show::getPopularity).reversed())
        .toList();
  }

  private Show mapToShow(TMDbSearchResultShow result, ShowType type) {
    var show = new Show();
    show.setId(result.getId());
    show.setTitle(result.getTitle());
    show.setReleaseDate(yearOf(result.getReleaseDate()));
    show.setPoster(POSTER_URI + result.getPoster());
    show.setPopularity(result.getPopularity());
    show.setShowType(type);
    return show;
  }

  public ShowDetails mapSearchDetails(TMDbSearchDetails details, ShowType type) {
    var showDetails = new ShowDetails();
    showDetails.setId(details.getId());
    showDetails.setTitle(details.getTitle());
    showDetails.setOriginalTitle(details.getOriginalTitle());
    showDetails.setOverview(details.getOverview());
    showDetails.setReleaseDate(yearOf(details.getReleaseDate()));
    showDetails.setDuration(durationOf(details.getRuntime(), type));
    showDetails.setGenres(details.getGenres());
    showDetails.setWatchProviders(mapWatchProviders(details.getWatchProviders()));
    showDetails.setShowType(type);
    return showDetails;
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
          case ALL -> null;
          case MOVIE -> "minutes";
          case SERIES -> "episodes";
        };
  }

  private String yearOf(String releaseDate) {
    if (releaseDate == null || releaseDate.isEmpty()) return "";
    else return String.valueOf(LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE).getYear());
  }
}
