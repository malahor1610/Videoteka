package com.github.malahor.videoteka.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ShowPresentOnWatchlistExceptionMapper
    implements ExceptionMapper<ShowPresentOnWatchlistException> {

  @Override
  public Response toResponse(ShowPresentOnWatchlistException exception) {
    return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
  }
}
