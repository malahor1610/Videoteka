package com.github.malahor.videoteka.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.malahor.videoteka.aws.domain.Show;
import com.github.malahor.videoteka.aws.exception.ShowsNotFoundException;
import com.github.malahor.videoteka.aws.request.SearchRequest;
import java.util.List;

public class SearchRequestHandler implements RequestHandler<SearchRequest, List<Show>> {

  @Override
  public List<Show> handleRequest(SearchRequest request, Context context) {
    var shows = new ShowService().search(request.getTitle(), request.getType(), apiKey());
    if (shows.isEmpty()) throw new ShowsNotFoundException();
    return shows;
  }

  private String apiKey() {
    return System.getenv().get("VIDEOTEKA_API_KEY");
  }
}
