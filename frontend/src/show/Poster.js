import '../App.css';

export default function Poster({ image }) {

  return (
    <img className='d-block img-fluid img-thumbnail' src={image} />
  );
}
