import '../App.css';

export default function Title({ show }) {

  return (
    <span className='small'>{show.title} {!show.originalTitle ? '' : '(' +show.originalTitle+')'} {!show.releaseDate ? '' : '(' +show.releaseDate+')'}</span>
  );
}
