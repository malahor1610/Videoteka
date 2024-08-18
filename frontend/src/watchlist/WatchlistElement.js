import { Card, CardBody, CardSubtitle, CardTitle, Col, Container, Row } from 'reactstrap';
import '../App.css';
import Duration from '../show/Duration';
import Poster from '../show/Poster';
import Title from '../show/Title';
import WatchProviders from '../show/WatchProviders';

export default function WatchlistElement({ show }) {

  return (
    <Container key={show.id} className='mb-2' style={{ 'max-width': '500px' }}>
      <Card color="dark" inverse>
        <Row>
          <Col xs='3'>
            <Poster image={show.poster} />
          </Col>
          <Col>
            <CardBody>
              <CardTitle tag="h4">
                <Title show={show} />
              </CardTitle>
              <CardSubtitle>
                <Duration duration={show.duration} />
              </CardSubtitle>
            </CardBody>
          </Col>
          <Col xs='1' className='ps-1 align-content-center border-start border-secondary border-2 rounded'>
              <i class="bi bi-grip-vertical fs-4 align-text-bottom text-secondary"></i>
          </Col>
        </Row>
      </Card>
    </Container>
  );
}
