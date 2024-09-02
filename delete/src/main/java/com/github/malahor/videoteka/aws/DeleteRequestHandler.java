package com.github.malahor.videoteka.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.malahor.videoteka.aws.request.DeleteRequest;

public class DeleteRequestHandler implements RequestHandler<DeleteRequest, String> {
  @Override
  public String handleRequest(DeleteRequest request, Context context) {
    new ShowService().deleteById(request.getId(), request.getUserId());
    return "success";
  }
}
