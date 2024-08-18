import { useEffect, useState } from 'react';
import '../App.css';
import WatchlistElement from './WatchlistElement';

export default function Watchlist({ type }) {
  const [shows, setShows] = useState([]);


  let searchParams = new URLSearchParams({
    sort: "position"
  }).toString();
  let uri = "/shows?";
  if (type !== "ALL") {
    searchParams = new URLSearchParams({
      type: type
    }).toString();
    uri = "/shows/search/byType?";
  }
  useEffect(() => {
    fetch(uri + searchParams)
      .then(result => result.json())
      .then(result => setShows(result._embedded.shows));
  }, []);

  return (
    <div className='mt-5 pt-5'>
      {shows.map(show =>
        <WatchlistElement show={show} />
      )}
    </div>
  );
}
