import { Container } from 'reactstrap';
import '../../App.css';
import SearchResult from './SearchResult';

export default function SearchResults({ shows }) {

  return (
    <Container className='row text-center'>
      {shows.map(show =>
        <SearchResult show={show}/>
      )}
    </Container>
  );
}
