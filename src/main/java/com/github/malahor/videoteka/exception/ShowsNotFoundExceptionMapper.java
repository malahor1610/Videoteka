package com.github.malahor.videoteka.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ShowsNotFoundExceptionMapper implements ExceptionMapper<ShowsNotFoundException> {

  @Override
  public Response toResponse(ShowsNotFoundException exception) {
    return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
  }
}
