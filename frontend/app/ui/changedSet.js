
export default function ChangedSet({ shows, text }) {
  return (
    <>
      {shows.length > 0 ? <h6>{text}</h6> : <></>}
      {shows.length > 0 ? shows.map((show) => <p key={show.id}>{show.title}</p>) : <></>}
    </>
  );
}
