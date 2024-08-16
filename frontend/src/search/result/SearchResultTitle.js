import '../../App.css';

export default function SearchResultTitle({ show }) {

  return (
    <span className='small'>{show.title} ({show.releaseDate})</span>
  );
}
