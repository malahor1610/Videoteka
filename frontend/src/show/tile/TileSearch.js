import { Button, Card, CardBody, CardTitle } from 'reactstrap';
import '../../App.css';
import Poster from '../Poster';
import Title from '../Title';

export default function TileSearch({ show, openModal }) {

  return (
    <Card color="dark" inverse>
      <Button className='opacity-100 p-0 p-sm-2' onClick={openModal}>
        <CardBody className='p-0 pb-1'>
          <Poster image={show.poster} />
          <CardTitle className='m-0' tag="h6">
            <Title show={show} />
          </CardTitle>
        </CardBody>
      </Button>
    </Card>
  );
}
