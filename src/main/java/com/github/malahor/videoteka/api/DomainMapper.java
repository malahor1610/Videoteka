package com.github.malahor.videoteka.api;

import com.github.malahor.videoteka.domain.*;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    show.setReleaseDate(yearOf(result.getReleaseDate()));
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
    showDetails.setReleaseDate(yearOf(details));
    showDetails.setPredictReleaseDate(predictionInfo(details, type));
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

  private String yearOf(String releaseDate) {
    if (releaseDate == null || releaseDate.isEmpty()) return "";
    else return String.valueOf(localDateOf(releaseDate).getYear());
  }

  private String yearOf(SearchDetails details) {
    return yearOf(determineReleaseDate(details));
  }

  private String determineReleaseDate(SearchDetails details) {
    return Optional.ofNullable(details.getReleaseDate())
        .or(() -> Optional.ofNullable(details.getPredictDate()))
        .orElse("");
  }

  private String predictionInfo(SearchDetails details, ShowType type) {
    return switch (type) {
      case MOVIE -> predictionInfoMovie(details);
      case SERIES -> predictionInfoSeries(details);
    };
  }

  private String predictionInfoMovie(SearchDetails details) {
    var releaseDate = details.getPredictDate();
    if (releaseDate == null || releaseDate.isEmpty()) return "Data premiery nieznana";
    if (localDateOf(releaseDate).isAfter(LocalDate.now()))
      return "Premiera " + accurateDate(releaseDate);
    return "";
  }

  private String predictionInfoSeries(SearchDetails details) {
    if (!Boolean.TRUE.equals(details.getInProduction())) return "";
    var releaseDate = details.getPredictDate();
    if (releaseDate == null || releaseDate.isEmpty()) return "Wraca wkrótce";
    else return "Powraca " + accurateDate(releaseDate);
  }

  private String accurateDate(String releaseDate) {
    return localDateOf(releaseDate).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
  }

  private LocalDate localDateOf(String releaseDate) {
    return LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE);
  }
}
