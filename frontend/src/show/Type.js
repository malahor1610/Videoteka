import { Button, ButtonGroup } from 'reactstrap';
import '../App.css';

export default function Type({type, setType}) {

  return (
    <ButtonGroup className='col-auto my-1'>
      <Button color="primary"
          outline
          onClick={() => setType("MOVIE")}
          active={type === "MOVIE"}>
            Movie
      </Button>
      <Button color="primary"
          outline
          onClick={() => setType("SERIES")}
          active={type === "SERIES"}>
            Series
      </Button>
    </ButtonGroup>
  );
}
