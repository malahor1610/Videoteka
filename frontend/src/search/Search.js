import { useState } from 'react';
import { Row } from 'reactstrap';
import '../App.css';
import Error from '../Error';
import SearchBar from './SearchBar';
import SearchResults from './SearchResults';

export default function Search() {
  const [shows, setShows] = useState([]);
  const [error, setError] = useState('');

  return (
    <Row className='w-100 mt-5 pt-5 justify-content-center'>
      <SearchBar setShows={setShows} />
      <SearchResults shows={shows} setError={setError} />
      <Error error={error} setError={setError} />
    </Row>

  );
}
