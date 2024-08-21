import { Button, Card, CardBody, CardSubtitle, CardTitle, Col, Container, Row } from 'reactstrap';
import '../App.css';
import Duration from './Duration';
import Poster from './Poster';
import Title from './Title';

export default function TileWatchlist({ show, openModal, orderable }) {

  const draggable = (
    <Col xs='1' className='ps-1 align-content-center border-start border-secondary border-2 rounded'>
      <i className="bi bi-grip-vertical fs-4 align-text-bottom text-secondary"></i>
    </Col>
  );

  return (
    <Container className='mb-2' style={{ 'maxWidth': '666px' }}>
      <Card className='mx-1' color="dark" inverse>
        <Row>
          <Col xs='3'>
            <Poster image={show.poster} />
          </Col>
          <Button className='col opacity-100' color='dark' onClick={openModal}>
            <CardBody>
              <CardTitle tag="h4">
                <Title show={show} />
              </CardTitle>
              <CardSubtitle>
                <Duration duration={show.duration} />
              </CardSubtitle>
            </CardBody>
          </Button>
          {orderable ? draggable : <></>}
        </Row>
      </Card>
    </Container>
  );
}
