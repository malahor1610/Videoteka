import { useEffect, useState } from 'react';
import '../App.css';
import WatchlistOrderable from './WatchlistOrderable';

export default function Watchlist() {
  const [shows, setShows] = useState([]);

  useEffect(() => {
    const searchParams = new URLSearchParams({
      sort: "position"
    }).toString();
    fetch("/api/shows?" + searchParams)
      .then(result => result.json())
      .then(result => setShows(result._embedded.shows));
  }, []);

  return (
    <WatchlistOrderable shows={shows} setShows={setShows} />
  );
}
