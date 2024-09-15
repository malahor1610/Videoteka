import { Badge } from 'reactstrap';

export default function Genres({ genres }) {

  return (
    <p>
      {genres?.map(genre =>
        <Badge key={genre} className='me-1'>{genre}</Badge>
      )}
    </p>
  );
}