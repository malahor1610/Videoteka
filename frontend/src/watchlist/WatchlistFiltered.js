import { useCallback, useEffect, useState } from 'react';
import '../App.css';
import Page from '../shell/Page';
import Tile from '../show/tile/Tile';
import Notification, { hide } from '../shell/Notification';

export default function WatchlistFiltered({ type }) {
  const [shows, setShows] = useState([]);
  const [message, setMessage] = useState(hide());

  const fetchShows = useCallback(() => {
    const searchParams = new URLSearchParams({
      type: type
    }).toString();
    fetch("/api/shows/search/byType?" + searchParams)
      .then(result => result.json())
      .then(result => setShows(result._embedded.shows));
  }, [type])

  useEffect(() => {
    fetchShows();
  }, [fetchShows]);

  return (
    <Page content={(<>
      <div className='w-100 p-0'>
        {shows.map((show) =>
          <Tile key={show.id} show={show} setMessage={setMessage} fetchShows={fetchShows} />
        )}
      </div>
      <Notification message={message.message} type={message.type} setMessage={setMessage} />
    </>)} />
  );
}
