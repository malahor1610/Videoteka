import { useState } from 'react';
import '../App.css';
import Page from '../shell/Page';
import RandomBar from './RandomBar';
import RandomOptions from './RandomOptions';
import RandomPicked from './RandomPicked';

export default function Random() {
  const [shows, setShows] = useState([]);

  return shows.length === 1 ?
    (<Page content={(<>
      <RandomBar shows={shows} setShows={setShows} pickDisabled />
      <RandomPicked show={shows[0]} />
    </>)} />) :
    (<Page content={(<>
      <RandomBar shows={shows} setShows={setShows} />
      <RandomOptions shows={shows} setShows={setShows} />
    </>)} />);
}
