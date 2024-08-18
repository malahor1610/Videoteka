import '../App.css';

export default function Poster({ image, small }) {

  return (
    small ? Small(image = { image }) : Big(image = { image })
  );
}

function Small({ image }) {
  return (
    <img className='d-block img-fluid border border-secondary border-2 rounded' src={image} alt='No poster available' />
  );
}

function Big({ image }) {
  return (
    <img className='d-block img-fluid border border-3 rounded' src={image} alt='No poster available'/>
  );
}
