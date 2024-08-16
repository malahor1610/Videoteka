import { Row } from 'reactstrap';
import '../../App.css';

export default function SearchResultDetailsProvider({ text, providers }) {

  return (
    <div>
      <p className='mt-2 mb-0'>{text}</p>
      <Row>
        {providers?.map(provider =>
          <img className='col-lg-1 col-2 my-1 ' src={provider} />
        )}
      </Row>
    </div>
  );
}
