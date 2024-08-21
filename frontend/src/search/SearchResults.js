import { Col, Container } from 'reactstrap';
import '../App.css';
import SearchResult from './SearchResult';

export default function SearchResults({ shows, setError }) {

  return (
    <Container className='row text-center'>
      {shows.map(show =>
        <Col xs='2' className='my-2' key={show.id}>
          <SearchResult show={show} setError={setError} />
        </Col>
      )}
    </Container>
  );
}
