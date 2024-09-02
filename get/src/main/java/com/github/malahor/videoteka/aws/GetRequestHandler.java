package com.github.malahor.videoteka.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.malahor.videoteka.aws.domain.ShowEntity;
import com.github.malahor.videoteka.aws.domain.ShowType;
import com.github.malahor.videoteka.aws.request.GetRequest;

import java.util.List;

public class GetRequestHandler implements RequestHandler<GetRequest, List<ShowEntity>> {
  @Override
  public List<ShowEntity> handleRequest(GetRequest request, Context context) {
    var type = request.getType();
    var user = request.getUserId();
    if(type.equals(ShowType.ALL)) return new ShowService().findAll(user);
    return new ShowService().findByType(type, user);
  }
}
