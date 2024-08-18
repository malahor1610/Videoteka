import { Row } from 'reactstrap';
import '../App.css';

export default function WatchProviders({ type, providers }) {

  return (
    <div>
      {type}
      <Row>
        {providers?.map(provider =>
          <img key={provider} className='col-lg-1 col-2 my-1 ' src={provider} />
        )}
      </Row>
    </div>
  );
}
