package com.github.malahor.videoteka.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.malahor.videoteka.aws.domain.ShowEntity;

public class AddRequestHandler implements RequestHandler<ShowEntity, String> {
  @Override
  public String handleRequest(ShowEntity request, Context context) {
    new ShowService().save(request);
    return "success";
  }
}
