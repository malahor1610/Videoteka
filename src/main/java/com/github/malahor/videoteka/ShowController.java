package com.github.malahor.videoteka;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowLockState;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.exception.ShowPresentOnWatchlistException;
import com.github.malahor.videoteka.repository.ShowRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.eclipse.microprofile.jwt.JsonWebToken;

@RequestScoped
@Path("/api/shows")
@Slf4j
@RequiredArgsConstructor
public class ShowController {

  private final ShowRepository repository;
  private final JsonWebToken jwt;
  private final ApiService apiService;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response save(ShowEntity show) {
    var username = getUserId();
    var shows = repository.findAll(username);
    if (shows.stream().map(ShowEntity::getId).anyMatch(s -> s.equals(show.getId())))
      throw new ShowPresentOnWatchlistException();
    show.setPosition(maxPosition(shows));
    show.setUserId(username);
    repository.save(show);
    return Response.ok(show).build();
  }

  private int maxPosition(List<ShowEntity> shows) {
    var maxPosition = shows.isEmpty() ? 0 : shows.getLast().getPosition();
    maxPosition++;
    return maxPosition;
  }

  @PUT
  @Path("/lock/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateLock(@PathParam("id") long id) {
    var username = getUserId();
    var show = repository.findById(id, username);
    var details = apiService.details(id, ShowType.SERIES);
    show.setLockState(ShowLockState.lockByDetails(details));
    repository.update(show);
    return Response.ok(show).build();
  }

  @PUT
  @Path("/unlock/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUnlock(@PathParam("id") long id) {
    var username = getUserId();
    var show = repository.findById(id, username);
    show.setLockState(ShowLockState.UNLOCKED);
    repository.update(show);
    return Response.ok(show).build();
  }

  @PUT
  @Path("/locks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateLocks(List<ShowEntity> shows) {
    var username = getUserId();
    shows.forEach(
        show -> {
          var dbShow = repository.findById(show.getId(), username);
          dbShow.setLockState(show.getLockState().changed());
          repository.update(dbShow);
        });
    return Response.ok().build();
  }

  @PUT
  @Path("/positions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePositions(List<ShowEntity> shows) {
    var username = getUserId();
    var dbShows = repository.findAll(username);
    updatePositions(shows, dbShows);
    dbShows.sort(Comparator.comparingInt(ShowEntity::getPosition));
    return Response.ok(dbShows).build();
  }

  private void updatePositions(List<ShowEntity> shows, List<ShowEntity> dbShows) {
    var changedShows = new ArrayList<ShowEntity>();
    for (var dbShow : dbShows) {
      int matchShowPosition =
          shows.stream()
              .filter(show -> show.getId() == dbShow.getId())
              .findFirst()
              .map(ShowEntity::getPosition)
              .orElse(dbShow.getPosition());
      if (matchShowPosition != dbShow.getPosition()) {
        dbShow.setPosition(matchShowPosition);
        changedShows.add(dbShow);
      }
    }
    var partitions = ListUtils.partition(changedShows, 100);
    partitions.forEach(repository::update);
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
