package com.github.malahor.videoteka.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.malahor.videoteka.aws.domain.ShowDetails;
import com.github.malahor.videoteka.aws.request.DetailsRequest;

public class DetailsRequestHandler implements RequestHandler<DetailsRequest, ShowDetails> {

  @Override
  public ShowDetails handleRequest(DetailsRequest request, Context context) {
    return new ShowService().details(request.getId(), request.getType(), apiKey());
  }

  private String apiKey() {
    return System.getenv().get("VIDEOTEKA_API_KEY");
  }
}
