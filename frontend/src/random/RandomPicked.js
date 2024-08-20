import { Button, Col } from 'reactstrap';
import '../App.css';
import Poster from '../show/Poster';
import Title from '../show/Title';
import { useState } from 'react';
import WatchlistElementDetails from '../watchlist/WatchlistElementDetails';

export default function RandomPicked({ show }) {

  const [modal, setModal] = useState(false);
  const [details, setDetails] = useState([]);

  function openModal() {
    const searchParams = new URLSearchParams({
      type: show.type
    }).toString();
    fetch("/api/search/" + show.id + "?" + searchParams)
      .then(result => result.json())
      .then(result => setDetails(result));
    setModal(!modal);
  }

  return (
    <Col xs='12' className='my-2'>
      <Button className='opacity-100' onClick={() => openModal()}>
        <Poster image={show.poster} small />
        <Title show={show} short />
      </Button>
      <WatchlistElementDetails show={details} modal={modal} setModal={setModal} />
    </Col>
  );
}
