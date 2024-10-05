package com.github.malahor.videoteka.api;

import com.github.malahor.videoteka.domain.*;
import jakarta.enterprise.context.ApplicationScoped;

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
    show.setReleaseDate(result.getReleaseDate().formatted());
    show.setPoster(POSTER_URI + result.getPoster());
    show.setPopularity(result.getPopularity());
    show.setShowType(type);
    return show;
  }

  public String mapSearchPoster(SearchPoster result) {
    return POSTER_URI + result.getPoster();
  }

  public ShowDetails mapSearchDetails(SearchDetails details, ShowType type) {
    var showDetails = new ShowDetails();
    showDetails.setId(details.getId());
    showDetails.setTitle(details.getTitle());
    showDetails.setOriginalTitle(details.getOriginalTitle());
    showDetails.setOverview(details.getOverview());
    showDetails.setReleaseDate(details.getReleaseDate().formatted());
    showDetails.setContinuation(mapContinuation(details, type));
    showDetails.setDuration(String.valueOf(details.getRuntime()));
    showDetails.setGenres(details.getGenres());
    showDetails.setWatchProviders(mapWatchProviders(details.getWatchProviders()));
    showDetails.setCollection(mapCollection(details.getCollection()));
    showDetails.setSeasons(mapSeasons(details.getSeasons()));
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

  private List<ShowSeason> mapSeasons(List<SearchSeason> tmdbSeasons) {
    if (tmdbSeasons == null) return null;
    return tmdbSeasons.stream()
        .filter(tmdbSeason -> tmdbSeason.getNumber() > 0)
        .filter(tmdbSeason -> tmdbSeason.getEpisodes() > 0)
        .map(
            tmdbSeason -> {
              var season = new ShowSeason();
              season.setNumber(tmdbSeason.getNumber());
              season.setName(tmdbSeason.getName());
              season.setEpisodes(tmdbSeason.getEpisodes());
              return season;
            })
        .toList();
  }

  private ShowContinuation mapContinuation(SearchDetails details, ShowType type) {
    if (type.equals(ShowType.SERIES)) return mapContinuation(details);
    return null;
  }

  private ShowContinuation mapContinuation(SearchDetails details) {
    var continuation = new ShowContinuation();
    continuation.setInProduction(details.getInProduction());
    if (!continuation.isInProduction() || details.getContinuation() == null) return continuation;
    continuation.setReleaseDate(details.getContinuation().getReleaseDate().formatted());
    continuation.setSeason(details.getContinuation().getSeason());
    return continuation;
  }
}
