export default function Continuation({ continuation }) {
  return !continuation?.inProduction ? (
    <></>
  ) : (
    <h5>
      {!continuation.releaseDate
        ? "Nowy sezon już wkrótce"
        : "Sezon " + continuation.season + " już " + continuation.releaseDate}
    </h5>
  );
}
