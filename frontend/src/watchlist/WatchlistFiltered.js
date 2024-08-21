import { useEffect, useState } from 'react';
import '../App.css';
import Page from '../shell/Page';
import Tile from '../show/Tile';

export default function WatchlistFiltered({ type }) {
  const [shows, setShows] = useState([]);

  useEffect(() => {
    const searchParams = new URLSearchParams({
      type: type
    }).toString();
    fetch("/api/shows/search/byType?" + searchParams)
      .then(result => result.json())
      .then(result => setShows(result._embedded.shows));
  }, [type]);

  return (
    <Page content={(<>
      <div className='w-100'>
        {shows.map((show) =>
          <Tile key={show.id} show={show} />
        )}
      </div>
    </>)} />
  );
}
