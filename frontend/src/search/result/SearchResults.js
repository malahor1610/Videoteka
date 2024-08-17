import { Container } from 'reactstrap';
import '../../App.css';
import SearchResult from './SearchResult';

export default function SearchResults({ shows }) {
  let colSize = '6';
  switch (shows.length) {
    case 1: colSize = '12'; break;
    case 2: colSize = '6'; break;
    case 3: colSize = '4'; break;
    default: colSize = '3'; break;
  }
  return (
    <Container className='row text-center'>
      {shows.map(show =>
        <SearchResult show={show} size={colSize} />
      )}
    </Container>
  );
}
