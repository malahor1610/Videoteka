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


  function updatePositions(list) {
    setShows(list);
    fetch('/api/shows/positions', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(list)
    })
      .then(response => response.json())
      .then(data => setShows(data))
      .catch(error => console.error('Error updating show:', error));
  }


  return (
    <WatchlistOrderable shows={shows} setOrder={updatePositions} />
  );
}
