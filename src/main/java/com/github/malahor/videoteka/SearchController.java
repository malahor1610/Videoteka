package com.github.malahor.videoteka;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.api.DomainMapper;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.exception.ShowsNotFoundException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/search")
public class SearchController {

  @Inject private ApiService service;

  @Inject private DomainMapper mapper;

  @GET
  public Response searchFor(@QueryParam("title") String title, @QueryParam("type") ShowType type) {
    var result = service.search(title, type);
    var shows = mapper.mapSearchResult(result, type);
    if (shows.isEmpty()) throw new ShowsNotFoundException();
    return Response.ok(shows).build();
  }

  @GET
  @Path("/{id}")
  public Response searchDetails(@PathParam("id") long id, @QueryParam("type") ShowType type) {
    var result = service.details(id, type);
    var details = mapper.mapSearchDetails(result, type);
    return Response.ok(details).build();
  }

  @GET
  @Path("/collection/{id}")
  public Response searchCollectionDetails(@PathParam("id") long id) {
    var result = service.collection(id);
    var details = mapper.mapSearchCollectionDetails(result);
    return Response.ok(details).build();
  }
}
