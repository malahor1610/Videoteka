import { useState } from 'react';
import { Button, Card, CardBody, CardSubtitle, CardTitle, Col, Container, Row } from 'reactstrap';
import '../App.css';
import Details from '../show/Details';
import Duration from '../show/Duration';
import Poster from '../show/Poster';
import Title from '../show/Title';
import WatchlistElementDrag from './WatchlistElementDrag';

export default function WatchlistElement({ show, orderable }) {

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

  function removeFromWatchlist() {
    fetch('/api/shows/' + show.id, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      }
    })
      .then(response => response.json())
      .then(data => console.log('Show removed:', data))
      .catch(error => console.error('Error removing show:', error));
    setModal(!modal);
    window.location.reload();
  }

  const removeButton = (
    <Button color="primary" onClick={() => removeFromWatchlist()}>
      Remove from watchlist
    </Button>
  );

  return (
    <Container className='mb-2' style={{ 'maxWidth': '666px' }}>
      <Card color="dark" inverse>
        <Row>
          <Col xs='3'>
            <Poster image={show.poster} small />
          </Col>
          <Button className='col' color='dark' onClick={() => openModal()}>
            <CardBody>
              <CardTitle tag="h4">
                <Title show={show} />
              </CardTitle>
              <CardSubtitle>
                <Duration duration={show.duration} />
              </CardSubtitle>
            </CardBody>
          </Button>
          {orderable ? <WatchlistElementDrag /> : <></>}
        </Row>
      </Card>
      <Details show={details} modal={modal} setModal={setModal} button={removeButton} />
    </Container>
  );
}
