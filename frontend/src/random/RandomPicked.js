import { useState } from 'react';
import { Button, Col } from 'reactstrap';
import '../App.css';
import Details from '../show/Details';
import Poster from '../show/Poster';
import Title from '../show/Title';

export default function RandomPicked({ show }) {
  const [details, setDetails] = useState([]);
  const [modal, setModal] = useState(false);

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
      <Button className='opacity-100' onClick={openModal}>
        <Poster image={show.poster} small />
        <Title show={show} short />
      </Button>
      <Details show={details} modal={modal} setModal={setModal} />
    </Col>
  );
}
