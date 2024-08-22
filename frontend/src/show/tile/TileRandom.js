import { Button } from 'reactstrap';
import '../../App.css';
import Poster from '../Poster';
import Title from '../Title';

export default function TileRandom({ show, openModal, disabled }) {

  return (
    <Button className='opacity-100' disabled={disabled} onClick={openModal}>
      <Poster image={show.poster} />
      <Title show={show} short />
    </Button>
  );
}
