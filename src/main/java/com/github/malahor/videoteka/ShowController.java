package com.github.malahor.videoteka;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowStatus;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.exception.ShowPresentOnWatchlistException;
import com.github.malahor.videoteka.repository.ShowRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Comparator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
@Path("/api/shows")
@Slf4j
public class ShowController {

  @Inject private ShowRepository repository;

  @Inject private JsonWebToken jwt;

  @Inject private ApiService apiService;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response save(ShowEntity show) {
    var username = getUserId();
    var shows = repository.findAll(username);
    if (shows.stream().map(ShowEntity::getId).anyMatch(s -> s.equals(show.getId())))
      throw new ShowPresentOnWatchlistException();
    var maxPosition = shows.isEmpty() ? 0 : shows.getLast().getPosition();
    maxPosition++;
    show.setPosition(maxPosition);
    show.setUserId(username);
    repository.save(show);
    return Response.ok(show).build();
  }

  @PUT
  @Path("/lock/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateLock(@PathParam("id") long id) {
    var username = getUserId();
    var show = repository.findById(id, username);
    var details = apiService.details(id, ShowType.SERIES);
    show.setStatus(ShowStatus.lockByDetails(details));
    repository.update(show);
    return Response.ok(show).build();
  }

  @PUT
  @Path("/unlock/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUnlock(@PathParam("id") long id) {
    var username = getUserId();
    var show = repository.findById(id, username);
    show.setStatus(ShowStatus.UNLOCKED);
    repository.update(show);
    return Response.ok(show).build();
  }

  @PUT
  @Path("/positions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePositions(List<ShowEntity> shows) {
    var username = getUserId();
    var dbShows = repository.findAll(username);
    dbShows.forEach(dbShow -> updatePosition(dbShow, shows));
    dbShows.sort(Comparator.comparingInt(ShowEntity::getPosition));
    return Response.ok(dbShows).build();
  }

  private void updatePosition(ShowEntity dbShow, List<ShowEntity> shows) {
    shows.stream()
        .filter(show -> show.getId() == dbShow.getId())
        .findFirst()
        .map(ShowEntity::getPosition)
        .ifPresent(
            position -> {
              if (dbShow.getPosition() != position) {
                dbShow.setPosition(position);
                repository.update(dbShow);
              }
            });
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    var username = getUserId();
    repository.delete(id, username);
    return Response.ok().build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAll() {
    var username = getUserId();
    var dbShows = (List<ShowEntity>) repository.findAll(username);
    return Response.ok(dbShows).build();
  }

  @GET
  @Path("/type/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getByType(@PathParam("type") ShowType type) {
    var username = getUserId();
    var dbShows = (List<ShowEntity>) repository.findByType(type, username);
    return Response.ok(dbShows).build();
  }

  private String getUserId() {
    return (String) jwt.claim("cognito:username").orElseGet(() -> jwt.getClaim("email"));
  }
}
