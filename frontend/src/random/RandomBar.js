import { Button, Row } from 'reactstrap';
import '../App.css';
import Type from '../show/Type';

export default function RandomBar({ type, setType, handleSubmit, fetchShows, pickDisabled }) {

  return (
    <Row className='justify-content-center mb-3'>
      <Type type={type} setType={setType} />
      <Button color="primary" className='col-auto mx-3' disabled={pickDisabled} onClick={handleSubmit}>Pick random</Button>
      <Button color="danger" className='col-auto mx-3' onClick={fetchShows}>Reset</Button>
    </Row>
  );
}
