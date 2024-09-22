package com.github.malahor.videoteka.api;

import com.github.malahor.videoteka.domain.*;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class DomainMapper {

  private static final String POSTER_URI = "http://image.tmdb.org/t/p/w342";

  public List<Show> mapSearchResult(SearchResult results, ShowType type) {
    return results.getResults().stream()
        .map(result -> mapToShow(result, type))
        .sorted(Comparator.comparing(Show::getPopularity).reversed())
        .toList();
  }

  private Show mapToShow(SearchResultShow result, ShowType type) {
    var show = new Show();
    show.setId(result.getId());
    show.setTitle(result.getTitle());
    show.setReleaseDate(mapReleaseDate(result.getReleaseDate()));
    show.setPoster(POSTER_URI + result.getPoster());
    show.setPopularity(result.getPopularity());
    show.setShowType(type);
    return show;
  }

  public ShowDetails mapSearchDetails(SearchDetails details, ShowType type) {
    var showDetails = new ShowDetails();
    showDetails.setId(details.getId());
    showDetails.setTitle(details.getTitle());
    showDetails.setOriginalTitle(details.getOriginalTitle());
    showDetails.setOverview(details.getOverview());
    showDetails.setReleaseDate(mapReleaseDate(details.getReleaseDate()));
    showDetails.setContinuation(mapContinuation(details, type));
    showDetails.setDuration(durationOf(details.getRuntime(), type));
    showDetails.setGenres(details.getGenres());
    showDetails.setWatchProviders(mapWatchProviders(details.getWatchProviders()));
    showDetails.setCollection(mapCollection(details.getCollection()));
    showDetails.setShowType(type);
    return showDetails;
  }

  public ShowCollectionDetails mapSearchCollectionDetails(SearchCollectionDetails tmdbCollection) {
    var collection = new ShowCollectionDetails();
    collection.setId(tmdbCollection.getId());
    collection.setName(tmdbCollection.getName());
    collection.setParts(
        tmdbCollection.getParts().stream()
            .map(part -> mapToShow(part, ShowType.MOVIE))
            .sorted(Comparator.comparing(Show::getReleaseDate))
            .toList());
    return collection;
  }

  private WatchProviders mapWatchProviders(SearchWatchProviders tmdbWatchProviders) {
    if (tmdbWatchProviders == null) return WatchProviders.noProviders();
    var watchProviders = new WatchProviders();
    watchProviders.setAvailable(tmdbWatchProviders.getFlatrate());
    watchProviders.setRent(tmdbWatchProviders.getRent());
    watchProviders.setBuy(tmdbWatchProviders.getBuy());
    return watchProviders;
  }

  private ShowCollection mapCollection(SearchCollection tmdbCollection) {
    if (tmdbCollection == null) return null;
    var collection = new ShowCollection();
    collection.setId(tmdbCollection.getId());
    collection.setName(tmdbCollection.getName());
    return collection;
  }

  private String durationOf(int duration, ShowType type) {
    return duration
        + " "
        + switch (type) {
          case MOVIE -> "minutes";
          case SERIES -> "episodes";
        };
  }

  private String mapReleaseDate(String releaseDate) {
    if (releaseDate == null || releaseDate.isEmpty()) return "";
    var localDate = localDateOf(releaseDate);
    if (localDate.isAfter(LocalDate.now())) return accurateDate(releaseDate);
    return String.valueOf(localDate.getYear());
  }

  private ShowContinuation mapContinuation(SearchDetails details, ShowType type) {
    if (type.equals(ShowType.SERIES)) return mapContinuation(details);
    return null;
  }

  private ShowContinuation mapContinuation(SearchDetails details) {
    var continuation = new ShowContinuation();
    continuation.setInProduction(details.getInProduction());
    if (!continuation.isInProduction() || details.getContinuation() == null) return continuation;
    continuation.setReleaseDate(accurateDate(details.getContinuation().getReleaseDate()));
    continuation.setSeason(details.getContinuation().getSeason());
    return continuation;
  }

  private String accurateDate(String releaseDate) {
    return localDateOf(releaseDate).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
  }

  private LocalDate localDateOf(String releaseDate) {
    return LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE);
  }
}
