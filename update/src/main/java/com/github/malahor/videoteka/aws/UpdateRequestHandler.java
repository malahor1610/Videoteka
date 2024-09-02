package com.github.malahor.videoteka.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.malahor.videoteka.aws.domain.ShowEntity;
import java.util.Comparator;
import java.util.List;

public class UpdateRequestHandler implements RequestHandler<List<ShowEntity>, List<ShowEntity>> {

  @Override
  public List<ShowEntity> handleRequest(List<ShowEntity> request, Context context) {
    var service = new ShowService();
    var dbShows = service.findAll(request.getFirst().getUserId());
    dbShows.forEach(dbShow -> updatePosition(dbShow, request));
    dbShows.forEach(service::updateShow);
    dbShows.sort(Comparator.comparingInt(ShowEntity::getPosition));
    return dbShows;
  }

  private void updatePosition(ShowEntity dbShow, List<ShowEntity> shows) {
    shows.stream()
        .filter(show -> show.getId() == dbShow.getId())
        .findFirst()
        .ifPresent(show -> dbShow.setPosition(show.getPosition()));
  }
}
