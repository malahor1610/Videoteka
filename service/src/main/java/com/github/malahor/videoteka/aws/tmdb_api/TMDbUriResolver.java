package com.github.malahor.videoteka.aws.tmdb_api;

import com.github.malahor.videoteka.aws.domain.ShowType;
import java.net.URI;

public class TMDbUriResolver {

  private static final String API_URI = "https://api.themoviedb.org/3";

  public URI search(String title, ShowType type) {
    var query =
        String.format(
            API_URI + "/search/%s?query=%s&include_adult=true",
            typePath(type),
            title.replaceAll("\\s", "%20"));
    return URI.create(query);
  }

  public URI details(long id, ShowType type) {
    var query =
        String.format(
            API_URI + "/%s/%d?append_to_response=watch/providers&language=pl-PL",
            typePath(type),
            id);
    return URI.create(query);
  }

  private String typePath(ShowType type) {
    return switch (type) {
      case ALL -> null;
      case MOVIE -> "movie";
      case SERIES -> "tv";
    };
  }
}