import { useEffect, useState } from 'react';
import '../App.css';
import RandomBar from './RandomBar';
import RandomElement from './RandomElement';
import { Row } from 'reactstrap';
import RandomPicked from './RandomPicked';

export default function Random() {
  const [type, setType] = useState('MOVIE');
  const [shows, setShows] = useState([]);

  useEffect(() => {
    fetchShows();
  }, [type]);

  function fetchShows() {
    const searchParams = new URLSearchParams({
      type: type
    }).toString();
    fetch("/api/shows/search/byType?" + searchParams)
      .then(result => result.json())
      .then(result => setShows(result._embedded.shows));
  }

  function handleSubmit() {
    const result = Array.from(shows);
    const random = result[Math.floor(Math.random() * result.length)];
    setShows(Array.of(random));
  }

  function excludeShow(e, show) {
    const result = Array.from(shows);
    result.splice(show, 1);
    setShows(result);
  }

  return shows.length <= 1 ?
    (<Row className='mt-5 pt-5 w-100'>
      <RandomBar type={type} setType={setType} handleSubmit={handleSubmit} fetchShows={fetchShows} pickDisabled />
      <RandomPicked show={shows[0]} />
    </Row>) : (
      <Row className='mt-5 pt-5 w-100'>
        <RandomBar type={type} setType={setType} handleSubmit={handleSubmit} fetchShows={fetchShows} />
        {shows.map((show, index) =>
          <RandomElement show={show} index={index} excludeShow={excludeShow} />
        )}
      </Row>
    );
}
