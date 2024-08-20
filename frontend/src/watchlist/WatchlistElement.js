import { useState } from 'react';
import { Button, Card, CardBody, CardSubtitle, CardTitle, Col, Container, Row } from 'reactstrap';
import '../App.css';
import Duration from '../show/Duration';
import Poster from '../show/Poster';
import Title from '../show/Title';
import WatchlistElementDetails from './WatchlistElementDetails';
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
      <WatchlistElementDetails show={details} modal={modal} setModal={setModal} />
    </Container>
  );
}
