package com.github.malahor.videoteka;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.exception.ShowsNotFoundException;
import com.github.malahor.videoteka.repository.ShowRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
@Path("/api/search")
@RequiredArgsConstructor
public class SearchController {

  private final ApiService service;
  private final ShowRepository repository;
  private final JsonWebToken jwt;

  @GET
  public Response searchFor(@QueryParam("title") String title, @QueryParam("type") ShowType type) {
    var shows = service.search(title, type);
    if (shows.isEmpty()) throw new ShowsNotFoundException();
    return Response.ok(shows).build();
  }

  @GET
  @Path("/{id}")
  public Response searchDetails(@PathParam("id") long id, @QueryParam("type") ShowType type) {
    var username = getUserId();
    var details = service.details(id, type);
    var show = repository.findById(id, username);
    if(show != null) details.setWatchState(show.getWatchState());
    return Response.ok(details).build();
  }

  @GET
  @Path("/collection/{id}")
  public Response searchCollectionDetails(@PathParam("id") long id) {
    var details = service.collection(id);
    return Response.ok(details).build();
  }

  private String getUserId() {
    return (String) jwt.claim("cognito:username").orElseGet(() -> jwt.getClaim("email"));
  }
}
