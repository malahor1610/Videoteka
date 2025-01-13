package com.github.malahor.videoteka;

import static com.github.malahor.videoteka.domain.ShowLockState.UNLOCKED;
import static com.github.malahor.videoteka.domain.ShowType.SERIES;
import static com.github.malahor.videoteka.domain.ShowWatchState.*;

import com.github.malahor.videoteka.api.ApiService;
import com.github.malahor.videoteka.domain.ShowEntity;
import com.github.malahor.videoteka.domain.ShowLockState;
import com.github.malahor.videoteka.domain.ShowType;
import com.github.malahor.videoteka.exception.ShowPresentOnWatchlistException;
import com.github.malahor.videoteka.repository.ShowRepository;
import com.github.malahor.videoteka.util.MemoryLog;
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
@MemoryLog
public class ShowController {

  private final ShowRepository repository;
  private final ApiService apiService;
  private final UserProvider userProvider;

  @POST
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response save(@PathParam("id") long id, @QueryParam("type") ShowType type) {
    ShowEntity show;
    var username = userProvider.getUsername();
    var shows = repository.findAll(username);
    var match = shows.stream().filter(s -> s.getId() == id).findFirst();
    if (match.isPresent()) {
      show = match.get();
      if (!show.isWatchedNotOnList()) throw new ShowPresentOnWatchlistException();
      else repository.update(show.withMaxPositionOf(shows).withWatchState(WATCHED_ON_LIST));
    } else {
      show = createShow(id, type, username).withMaxPositionOf(shows);
      repository.save(show);
    }
    return Response.ok(show).build();
  }

  @PUT
  @Path("/watched/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateWatched(@PathParam("id") long id, @QueryParam("type") ShowType type) {
    var username = userProvider.getUsername();
    var show = repository.findById(id, username);
    if (show == null) {
      show = createShow(id, type, username).withWatchState(WATCHED);
      repository.save(show);
    } else {
      repository.update(show.withWatchState(WATCHED_ON_LIST));
    }
    return Response.ok(show).build();
  }

  @PUT
  @Path("/unwatched/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUnwatched(@PathParam("id") long id) {
    var username = userProvider.getUsername();
    var show = repository.findById(id, username);
    if (show.isWatchedOnList()) repository.update(show.withWatchState(UNWATCHED));
    if (show.isWatchedNotOnList()) repository.delete(show.getId(), username);
    return Response.ok(show).build();
  }

  @PUT
  @Path("/lock/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateLock(@PathParam("id") long id, @QueryParam("type") ShowType type) {
    var username = userProvider.getUsername();
    var show = repository.findById(id, username);
    var details = apiService.details(id, type);
    repository.update(show.withLockState(ShowLockState.lockByDetails(details)));
    return Response.ok(show).build();
  }

  @PUT
  @Path("/unlock/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUnlock(@PathParam("id") long id) {
    var username = userProvider.getUsername();
    var show = repository.findById(id, username);
    repository.update(show.withLockState(UNLOCKED));
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
          repository.update(dbShow.withLockState(show.getLockState().changed()));
        });
    return Response.ok().build();
  }

  @PUT
  @Path("/positions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePositions(List<ShowEntity> shows) {
    var username = userProvider.getUsername();
    var dbShows = repository.findWatchlist(username);
    updatePositions(dbShows, shows);
    dbShows.sort(Comparator.comparingInt(ShowEntity::getPosition));
    return Response.ok(dbShows).build();
  }

  private void updatePositions(List<ShowEntity> dbShows, List<ShowEntity> shows) {
    var showsToUpdate = new ArrayList<ShowEntity>();
    for (var dbShow : dbShows) {
      int newPosition = findMatchingShowPosition(shows, dbShow);
      if (newPosition != dbShow.getPosition()) showsToUpdate.add(dbShow.withPosition(newPosition));
    }
    var partitions = ListUtils.partition(showsToUpdate, 100);
    partitions.forEach(repository::update);
  }

  private int findMatchingShowPosition(List<ShowEntity> shows, ShowEntity dbShow) {
    return shows.stream()
        .filter(show -> show.getId() == dbShow.getId())
        .findFirst()
        .map(ShowEntity::getPosition)
        .orElse(dbShow.getPosition());
  }

  @DELETE
  @Path("/{id}")
  public Response delete(@PathParam("id") Long id) {
    var username = userProvider.getUsername();
    var show = repository.findById(id, username);
    if (show.isWatchedOnList()) repository.update(show.withWatchState(WATCHED));
    else repository.delete(show.getId(), username);
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
  @Path("/released/{type}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getReleasedByType(@PathParam("type") ShowType type) {
    var username = userProvider.getUsername();
    var dbShows = repository.findReleasedByType(type, username);
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

  private ShowEntity createShow(long id, ShowType type, String username) {
    var poster = apiService.poster(id, type);
    var details = apiService.details(id, type);
    return ShowEntity.from(details, poster, username);
  }
}
