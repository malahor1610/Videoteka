package com.github.malahor.videoteka;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowLockState;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.domain.ShowWatchState;
import com.github.malahor.videoteka.exception.ShowPresentOnWatchlistException;
import com.github.malahor.videoteka.repository.ShowRepository;
import com.github.malahor.videoteka.util.UserProvider;
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

@RequestScoped
@Path("/api/shows")
@Slf4j
@RequiredArgsConstructor
public class ShowController {

  private final ShowRepository repository;
  private final ApiService apiService;
  private final UserProvider userProvider;

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public Response save(ShowEntity show) {
    var username = userProvider.getUsername();
    var shows = repository.findAll(username);
    var match = shows.stream().filter(s -> s.getId() == show.getId()).findFirst();
    if (match.isPresent()) {
      var toUpdate = match.get();
      if (ShowWatchState.WATCHED.equals(match.get().getWatchState())) {
        toUpdate.setWatchState(ShowWatchState.WATCHED_ON_LIST);
        repository.update(toUpdate);
      } else throw new ShowPresentOnWatchlistException();
    } else {
      show.setPosition(maxPosition(shows));
      show.setUserId(username);
      repository.save(show);
    }
    return Response.ok(show).build();
  }

  private int maxPosition(List<ShowEntity> shows) {
    var maxPosition = shows.isEmpty() ? 0 : shows.getLast().getPosition();
    maxPosition++;
    return maxPosition;
  }

  @PUT
  @Path("/watched/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateWatched(@PathParam("id") long id, @QueryParam("type") ShowType type) {
    var username = userProvider.getUsername();
    var show = repository.findById(id, username);
    if (show == null) {
      show = findShow(id, type);
      show.setUserId(username);
      show.setWatchState(ShowWatchState.WATCHED);
      repository.save(show);
    } else {
      show.setWatchState(ShowWatchState.WATCHED_ON_LIST);
      repository.update(show);
    }
    return Response.ok(show).build();
  }

  @PUT
  @Path("/unwatched/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUnwatched(@PathParam("id") long id) {
    var username = userProvider.getUsername();
    var show = repository.findById(id, username);
    if (ShowWatchState.WATCHED_ON_LIST.equals(show.getWatchState())) {
      show.setWatchState(ShowWatchState.UNWATCHED);
      repository.update(show);
    } else if (ShowWatchState.WATCHED.equals(show.getWatchState()))
      show.setWatchState(ShowWatchState.UNWATCHED);
    repository.delete(show.getId(), username);
    return Response.ok(show).build();
  }

  @PUT
  @Path("/lock/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateLock(@PathParam("id") long id) {
    var username = userProvider.getUsername();
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
    var username = userProvider.getUsername();
    var show = repository.findById(id, username);
    show.setLockState(ShowLockState.UNLOCKED);
    repository.update(show);
    return Response.ok(show).build();
  }

  @PUT
  @Path("/locks")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateLocks(List<ShowEntity> shows) {
    var username = userProvider.getUsername();
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
    var username = userProvider.getUsername();
    var dbShows = repository.findWatchlist(username);
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
    var username = userProvider.getUsername();
    var show = repository.findById(id, username);
    if (ShowWatchState.WATCHED_ON_LIST.equals(show.getWatchState())) {
      show.setWatchState(ShowWatchState.WATCHED);
      repository.update(show);
    } else repository.delete(show.getId(), username);
    return Response.ok().build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAll() {
    var username = userProvider.getUsername();
    var dbShows = (List<ShowEntity>) repository.findWatchlist(username);
    return Response.ok(dbShows).build();
  }

  @GET
  @Path("/type/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getByType(@PathParam("type") ShowType type) {
    var username = userProvider.getUsername();
    var dbShows = (List<ShowEntity>) repository.findByType(type, username);
    return Response.ok(dbShows).build();
  }

  @GET
  @Path("/watched/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getWatchedByType(@PathParam("type") ShowType type) {
    var username = userProvider.getUsername();
    var dbShows = (List<ShowEntity>) repository.findWatchedByType(type, username);
    return Response.ok(dbShows).build();
  }

  private ShowEntity findShow(long id, ShowType type) {
    var poster = apiService.poster(id, type);
    var details = apiService.details(id, type);
    return ShowEntity.from(details, poster);
  }
}
