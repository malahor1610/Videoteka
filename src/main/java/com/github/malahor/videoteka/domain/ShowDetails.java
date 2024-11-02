package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.api.SearchDetails;
import com.github.malahor.videoteka.api.SearchSeason;
import java.util.List;
import java.util.Optional;
import lombok.*;

@Data
@Builder
public class ShowDetails {

  private long id;
  private String originalTitle;
  private String title;
  private String overview;
  private String releaseDate;
  private ShowContinuation continuation;
  private ShowLockState status;
  private String duration;
  private ShowType showType;
  private List<String> genres;
  private ShowWatchProviders watchProviders;
  private ShowCollection collection;
  private List<ShowSeason> seasons;
  private ShowWatchState watchState;

  public static ShowDetails from(SearchDetails details, ShowType type) {
    return ShowDetails.builder()
        .id(details.getId())
        .originalTitle(details.getOriginalTitle())
        .title(details.getTitle())
        .overview(details.getOverview())
        .releaseDate(details.getReleaseDate().formatted())
        .continuation(ShowContinuation.from(details, type))
        .duration(String.valueOf(details.getRuntime()))
        .showType(type)
        .genres(details.getGenres())
        .watchProviders(ShowWatchProviders.from(details.getWatchProviders()))
        .collection(ShowCollection.from(details.getCollection()))
        .seasons(
            Optional.ofNullable(details.getSeasons())
                .map(
                    detailsSeasons ->
                        detailsSeasons.stream()
                            .filter(SearchSeason::isApplicable)
                            .map(ShowSeason::from)
                            .toList())
                .orElse(null))
        .build();
  }

  public void withWatchState(ShowWatchState watchState) {
    this.watchState = Optional.ofNullable(watchState).orElse(ShowWatchState.UNWATCHED);
  }
}
