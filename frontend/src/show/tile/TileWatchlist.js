import { Button, Card, CardBody, CardSubtitle, CardTitle, Col, Container, Row } from 'reactstrap';
import '../../App.css';
import Duration from '../Duration';
import Poster from '../Poster';
import Title from '../Title';

export default function TileWatchlist({ show, openModal, orderable }) {

  const draggable = (
    <Col xs='1' className='px-1 align-content-center border-start border-secondary border-2 rounded'>
      <i className="bi bi-grip-vertical d-block d-sm-none fs-6 align-text-bottom text-secondary"></i>
      <i className="bi bi-grip-vertical d-none d-sm-block fs-4 align-text-bottom text-secondary"></i>
    </Col>
  );

  return (
    <Container className='px-1 mt-2 mb-2' style={{ 'maxWidth': '666px' }}>
      <Card className='px-1' color="dark" inverse>
        <Row className='px-1'>
          <Col xs='4' sm='3' className='p-0'>
            <Poster image={show.poster} />
          </Col>
          <Button className='col opacity-100' color='dark' onClick={openModal}>
            <CardBody className='p-0'>
              <CardTitle className='d-block d-sm-none h6'>
                <Title show={show} />
              </CardTitle>
              <CardTitle className='d-none d-sm-block h4'>
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
