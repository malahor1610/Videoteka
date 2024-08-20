import { Button, Col } from 'reactstrap';
import '../App.css';
import Poster from '../show/Poster';
import Title from '../show/Title';

export default function RandomPicked({ show }) {

  return (
    <Col xs='12' className='my-2'>
      <Button className='opacity-100' disabled>
        <Poster image={show.poster} small />
        <Title show={show} short />
      </Button>
    </Col>
  );
}
