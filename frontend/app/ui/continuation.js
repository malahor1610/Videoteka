export default function Continuation({ continuation, showType }) {
  const seriesEpisodePremiere = (
    <h5>{"Nowy odcinek już " + continuation.releaseDate}</h5>
  );

  const seriesDatePremiere = (
    <h5>{"Premiera sezonu już " + continuation.releaseDate}</h5>
  );

  const seriesPremiere = <h5>{"Premiera sezonu już wkrótce"}</h5>;

  const movieDatePremiere = (
    <h5>{"Premiera już " + continuation.releaseDate}</h5>
  );

  const moviePremiere = <h5>{"Premiera już wkrótce"}</h5>;

  return !continuation?.inProduction ? (
    <></>
  ) : showType === "MOVIE" ? (
    MovieContinuation((continuation = { continuation }))
  ) : (
    SeriesContinuation((continuation = { continuation }))
  );

  function MovieContinuation({ continuation }) {
    return !continuation.releaseDate ? moviePremiere : movieDatePremiere;
  }

  function SeriesContinuation({ continuation }) {
    return !continuation.releaseDate
      ? seriesPremiere
      : continuation.season > 0
      ? seriesDatePremiere
      : seriesEpisodePremiere;
  }
}
