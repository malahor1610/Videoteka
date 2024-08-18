import { Badge } from 'reactstrap';
import '../App.css';

export default function Genres({ genres }) {

  return (
    <p>
      {genres?.map(genre =>
        <Badge className='me-1'>{genre}</Badge>
      )}
    </p>
  );
}
