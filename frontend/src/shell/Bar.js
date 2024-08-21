import { Row } from 'reactstrap';
import '../App.css';

export default function Bar({ content }) {

  return (
    <Row className='justify-content-center my-3'>
      {content}
    </Row>
  );
}
