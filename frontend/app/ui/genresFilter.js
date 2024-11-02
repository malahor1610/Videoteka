"use client";
import { Button } from "reactstrap";

export default function GenresFilters({
  allShows,
  setShows,
  setExcluded,
  genres,
  setGenres,
}) {
  function activateGenre(genre) {
    let list = Array.from(genres);
    list[list.indexOf(genre)].active = !list[list.indexOf(genre)].active;
    list[list.indexOf(genre)].excluded = false;
    setGenres(list);
    filterOut();
  }

  function excludeGenre(e, genre) {
    e.preventDefault();
    let list = Array.from(genres);
    list[list.indexOf(genre)].active = false;
    list[list.indexOf(genre)].excluded = !list[list.indexOf(genre)].excluded;
    setGenres(list);
    filterOut();
  }

  function filterOut() {
    let activeGenres = genres.filter((genre) => genre.active);
    let excludedGenres = genres.filter((genre) => genre.excluded);
    let filtered = Array.from(allShows);
    filtered = filtered
      .filter((show) =>
        activeGenres.every((genre) => show.genres.indexOf(genre.name) > -1)
      )
      .filter((show) =>
        excludedGenres.every((genre) => show.genres.indexOf(genre.name) === -1)
      );
    setShows(filtered);
    if (setExcluded) {
      let excluded = Array.from(allShows);
      excluded = excluded.filter(
        (show) =>
          activeGenres.some((genre) => show.genres.indexOf(genre.name) === -1) ||
          excludedGenres.some((genre) => show.genres.indexOf(genre.name) > -1)
      );
      setExcluded(excluded);
    }
  }

  return (
    <>
      {genres.map((genre) => (
        <Button
          key={genre.name}
          className="m-1 text-white"
          color={
            genre.excluded ? "danger" : genre.active ? "success" : "secondary"
          }
          outline
          size="sm"
          active={genre.active || genre.excluded}
          onClick={() => activateGenre(genre)}
          onContextMenu={(e) => excludeGenre(e, genre)}
        >
          {genre.name}
        </Button>
      ))}
    </>
  );
}
