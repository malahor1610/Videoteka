import { useState } from 'react';
import '../App.css';
import SearchBar from './bar/SearchBar';
import SearchResults from './result/SearchResults';
import { Col } from 'reactstrap';

export default function Search() {
  const [shows, setShows] = useState([]);

  return (
    <Col xs='auto' className='mt-5 pt-5'>
      <SearchBar setShows={setShows}/>
      <SearchResults shows={shows}/>
    </Col>
  );
}
