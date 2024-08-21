import { useState } from 'react';
import { Row } from 'reactstrap';
import '../App.css';
import RandomBar from './RandomBar';
import RandomElement from './RandomElement';
import RandomPicked from './RandomPicked';

export default function Random() {
  const [shows, setShows] = useState([]);

  function excludeShow(e, show) {
    const result = Array.from(shows);
    result.splice(show, 1);
    setShows(result);
  }

  return shows.length === 1 ?
    (<Row className='mt-5 pt-5 w-100'>
      <RandomBar shows={shows} setShows={setShows} pickDisabled />
      <RandomPicked show={shows[0]} />
    </Row>) : (
      <Row className='mt-5 pt-5 w-100'>
        <RandomBar shows={shows} setShows={setShows} />
        {shows.map((show, index) =>
          <RandomElement show={show} index={index} excludeShow={excludeShow} />
        )}
      </Row>
    );
}
