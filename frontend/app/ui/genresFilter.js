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
    setGenres(list);
    let activeGenres = genres.filter((genre) => genre.active);
    let filtered = Array.from(allShows);
    filtered = filtered.filter((show) =>
      activeGenres.every((genre) => show.genres.indexOf(genre.name) > -1)
    );
    setShows(filtered);
    if (setExcluded) {
      let excluded = Array.from(allShows);
      excluded = excluded.filter((show) =>
        activeGenres.some((genre) => show.genres.indexOf(genre.name) === -1)
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
          color="secondary"
          outline
          size="sm"
          active={genre.active}
          onClick={() => activateGenre(genre)}
        >
          {genre.name}
        </Button>
      ))}
    </>
  );
}
