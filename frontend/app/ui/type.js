import { Button, ButtonGroup } from 'reactstrap';

export default function Type({type, setType}) {

  return (
    <ButtonGroup className='col-auto my-1'>
      <Button color="primary"
          outline
          onClick={() => setType("MOVIE")}
          active={type === "MOVIE"}>
            Film
      </Button>
      <Button color="primary"
          outline
          onClick={() => setType("SERIES")}
          active={type === "SERIES"}>
            Serial
      </Button>
    </ButtonGroup>
  );
}