import { useState } from 'react';
import { Button, Col } from 'reactstrap';
import '../../App.css';
import SearchResultDetails from './SearchResultDetails';
import Poster from '../../show/Poster';
import Title from '../../show/Title';

export default function SearchResult({ show, size }) {

  const [modal, setModal] = useState(false);
  const [details, setDetails] = useState([]);

  function openModal() {
    const searchParams = new URLSearchParams({
      type: show.type
    }).toString();
    fetch("/search/" + show.id + "?" + searchParams)
      .then(result => result.json())
      .then(result => setDetails(result));
    setModal(!modal);
  }

  return (
    <Col xs={size} className='my-2' key={show.id}>
      <Button onClick={() => openModal()}>
        <Poster image={show.poster} />
        <Title show={show} />
      </Button>
      <SearchResultDetails show={details} poster={show.poster} modal={modal} setModal={setModal} />
    </Col>
  );
}
