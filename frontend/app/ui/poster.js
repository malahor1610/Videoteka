
export default function Poster({ image }) {

  return (
    <img className='d-block img-fluid border border-secondary border-2 rounded' src={image} alt='No poster available' />
  );
}