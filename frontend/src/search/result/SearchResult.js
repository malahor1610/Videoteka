import { Col } from 'reactstrap';
import '../../App.css';
import SearchResultImage from './SearchResultImage';
import SearchResultTitle from './SearchResultTitle';

export default function SearchResult({ show }) {

  return (
    <Col xs='3' className='my-2 text-start' key={show.id}>
      <SearchResultImage poster={show.poster} />
      <SearchResultTitle show={show} />
    </Col>
  );
}
