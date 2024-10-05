package com.github.malahor.videoteka;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;

@Path("")
public class StaticWebController {

  @GET
  @Path("/")
  @Produces(MediaType.TEXT_HTML)
  public Response home() {
    return getResource("META-INF/resources/index.html");
  }

  @GET
  @Path("/watchlist")
  @Produces(MediaType.TEXT_HTML)
  public Response watchlist() {
    return getResource("META-INF/resources/watchlist.html");
  }

  @GET
  @Path("/search")
  @Produces(MediaType.TEXT_HTML)
  public Response searchPage() {
    return getResource("META-INF/resources/search.html");
  }

  @GET
  @Path("/watchlist/movies")
  @Produces(MediaType.TEXT_HTML)
  public Response moviesPage() {
    return getResource("META-INF/resources/watchlist/movies.html");
  }

  @GET
  @Path("/watchlist/series")
  @Produces(MediaType.TEXT_HTML)
  public Response seriesPage() {
    return getResource("META-INF/resources/watchlist/series.html");
  }

  @GET
  @Path("/random")
  @Produces(MediaType.TEXT_HTML)
  public Response randomPage() {
    return getResource("META-INF/resources/random.html");
  }

  @GET
  @Path("/collection")
  @Produces(MediaType.TEXT_HTML)
  public Response collectionPage() {
    return getResource("META-INF/resources/collection.html");
  }

  @GET
  @Path("/watched")
  @Produces(MediaType.TEXT_HTML)
  public Response watchedPage() {
    return getResource("META-INF/resources/watched.html");
  }

  private Response getResource(String name) {
    var resource = getClass().getClassLoader().getResourceAsStream(name);
    return resource == null
        ? Response.status(Response.Status.NOT_FOUND).build()
        : Response.ok(resource).build();
  }
}
