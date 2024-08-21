import { Row } from 'reactstrap';
import '../App.css';

export default function Page({content}) {

  return (
    <Row className='w-100 px-5 mt-5 pt-5 justify-content-center'>
      {content}
    </Row>
  );
}
