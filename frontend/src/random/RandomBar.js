import { useCallback, useEffect, useState } from 'react';
import { Button } from 'reactstrap';
import '../App.css';
import Bar from '../shell/Bar';
import Type from '../show/Type';

export default function RandomBar({ shows, setShows, pickDisabled }) {
  const [type, setType] = useState('MOVIE');

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

  function handleSubmit() {
    const result = Array.from(shows);
    const random = result[Math.floor(Math.random() * result.length)];
    setShows(Array.of(random));
  }

  return (
    <Bar content={(<>
      <Type type={type} setType={setType} />
      <Button color="primary" className='col-auto mx-3' disabled={pickDisabled} onClick={handleSubmit}>Pick random</Button>
      <Button color="danger" className='col-auto mx-3' onClick={fetchShows}>Reset</Button>
    </>)} />
  );
}
