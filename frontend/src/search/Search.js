import { useState } from 'react';
import { Row } from 'reactstrap';
import '../App.css';
import Error from '../shell/Error';
import SearchBar from './SearchBar';
import SearchResults from './SearchResults';
import Page from '../shell/Page';

export default function Search() {
  const [shows, setShows] = useState([]);
  const [error, setError] = useState('');

  return (
    <Page content={(<>
      <SearchBar setShows={setShows} />
      <SearchResults shows={shows} setError={setError} />
      <Error error={error} setError={setError} />
    </>
    )} />
  );
}
