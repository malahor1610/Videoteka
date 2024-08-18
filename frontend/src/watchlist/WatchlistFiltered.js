import { useEffect, useState } from 'react';
import '../App.css';
import WatchlistElement from './WatchlistElement';

export default function WatchlistFiltered({ type }) {
  const [shows, setShows] = useState([]);

  const searchParams = new URLSearchParams({
    type: type
  }).toString();
  useEffect(() => {
    fetch("/shows/search/byType?" + searchParams)
      .then(result => result.json())
      .then(result => setShows(result._embedded.shows));
  }, []);

  return (
    <div className='mt-5 pt-5'>
      {shows.map((show) =>
        <WatchlistElement key={show.id} show={show} />
      )}
    </div>
  );
}
