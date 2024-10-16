package com.github.malahor.videoteka.api;

import com.github.malahor.videoteka.domain.ShowType;
import jakarta.enterprise.context.ApplicationScoped;
import java.net.URI;

@ApplicationScoped
public class UriResolver {

  private static final String API_URI = "https://api.themoviedb.org/3/";
  private static final String SEARCH_API = API_URI + "search/%s?query=%s&include_adult=true";
  private static final String POSTER_API = API_URI + "%s/%d?language=en-US";
  private static final String DETAILS_API =
      API_URI + "%s/%d?append_to_response=watch/providers&language=pl-PL";
  private static final String COLLECTION_API = API_URI + "collection/%d";

  public URI search(String title, ShowType type) {
    var query = String.format(SEARCH_API, typePath(type), title.replaceAll("\\s", "%20"));
    return URI.create(query);
  }

  public URI poster(long id, ShowType type) {
    var query = String.format(POSTER_API, typePath(type), id);
    return URI.create(query);
  }

  public URI details(long id, ShowType type) {
    var query = String.format(DETAILS_API, typePath(type), id);
    return URI.create(query);
  }

  public URI collection(long id) {
    var query = String.format(COLLECTION_API, id);
    return URI.create(query);
  }

  private String typePath(ShowType type) {
    return switch (type) {
      case MOVIE -> "movie";
      case SERIES -> "tv";
    };
  }
}
