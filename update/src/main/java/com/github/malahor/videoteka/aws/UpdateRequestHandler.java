package com.github.malahor.videoteka.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.malahor.videoteka.aws.domain.ShowEntity;
import com.github.malahor.videoteka.aws.request.UpdateRequest;

import java.util.Comparator;
import java.util.List;

public class UpdateRequestHandler implements RequestHandler<UpdateRequest, List<ShowEntity>> {

  @Override
  public List<ShowEntity> handleRequest(UpdateRequest request, Context context) {
    LambdaLogger logger = context.getLogger();
    var service = new ShowService();
    logger.log(request.toString());
    var dbShows = service.findAll(request.getUserId());
    logger.log(dbShows.toString());
    dbShows.forEach(dbShow -> updatePosition(dbShow, request.getShows(), logger));
    logger.log(dbShows.toString());
    dbShows.forEach(service::updateShow);
    logger.log(dbShows.toString());
    dbShows.sort(Comparator.comparingInt(ShowEntity::getPosition));
    logger.log(dbShows.toString());
    return dbShows;
  }

  private void updatePosition(ShowEntity dbShow, List<ShowEntity> shows, LambdaLogger logger) {
    logger.log(dbShow.toString());
    shows.stream()
        .filter(show -> show.getId() == dbShow.getId())
        .findFirst()
        .ifPresent(show -> dbShow.setPosition(show.getPosition()));
  }
}
