package com.github.malahor.videoteka.domain;

import com.github.malahor.videoteka.api.SearchCollectionDetails;
import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowCollectionDetails {

  private long id;
  private String name;
  private List<Show> parts;

  public static ShowCollectionDetails from(SearchCollectionDetails collectionDetails) {
    return ShowCollectionDetails.builder()
        .id(collectionDetails.getId())
        .name(collectionDetails.getName())
        .parts(
            collectionDetails.getParts().stream()
                .map(part -> Show.from(part, ShowType.MOVIE))
                .sorted(Comparator.comparing(Show::getReleaseDate))
                .toList())
        .build();
  }
}
