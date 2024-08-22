import { Col, Container } from 'reactstrap';
import '../App.css';
import Tile from '../show/tile/Tile';

export default function SearchResults({ shows, setMessage }) {

  return (
    <Container className='row text-center'>
      {shows.map(show =>
        <Col xs='6' sm='6' md='4' lg='3' xxl='2' className='my-2' key={show.id}>
          <Tile show={show} search setMessage={setMessage}/>
        </Col>
      )}
    </Container>
  );
}
