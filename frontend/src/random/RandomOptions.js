import { Button, Col, Row } from 'reactstrap';
import '../App.css';
import Tile from '../show/tile/Tile';

export default function RandomOptions({ shows, setShows }) {

  function excludeShow(e, show) {
    const result = Array.from(shows);
    result.splice(show, 1);
    setShows(result);
  }

  return (<>
    {shows.map((show, index) =>
      <Col xs='2' className='my-2' key={show.id}>
        <Tile show={show} random disabled />
        <Row className='my-1 justify-content-center'>
          <Button className='bg-danger btn-close' onClick={(e) => excludeShow(e, index)} />
        </Row>
      </Col>
    )}
  </>);
}
