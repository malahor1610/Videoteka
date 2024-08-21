import { Col } from 'reactstrap';
import '../App.css';
import Tile from '../show/Tile';

export default function RandomPicked({ show }) {

  return (
    <Col xs='12' className='my-2'>
      <Tile show={show} random />
    </Col>
  );
}
