export default function Continuation({ continuation }) {
  return !continuation?.inProduction ? (
    <></>
  ) : (
    <h5>
      {!continuation.releaseDate
        ? "Nowy sezon już wkrótce"
        : (continuation.season > 0
            ? "Sezon " + continuation.season
            : "Nowy odcinek ") +
          " już " +
          continuation.releaseDate}
    </h5>
  );
}
