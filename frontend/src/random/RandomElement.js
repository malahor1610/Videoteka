import { Button, Col, Row } from 'reactstrap';
import '../App.css';
import Poster from '../show/Poster';
import Title from '../show/Title';

export default function RandomElement({ show, index, excludeShow }) {

  return (
    <Col xs='2' className='my-2' key={show.id}>
      <Button className='opacity-100' disabled>
        <Poster image={show.poster} small />
        <Title show={show} short />
      </Button>
      <Row className='my-1 justify-content-center'>
        <Button className='bg-danger btn-close' onClick={(e) => excludeShow(e, index)} />
      </Row>
    </Col>
  );
}
