
export default function Title({ show, short }) {


  return (
    short ? Short(show = { show }) : Long(show = { show })
  );
}

function Short({ show }) {
  return (
    <span className='small'>{!show.originalTitle ? show.title : show.originalTitle}</span>
  );
}

function Long({ show }) {
  return (
    <span className='small'>{show.title} {!show.originalTitle ? '' : '(' +show.originalTitle+')'} {!show.releaseDate ? '' : '(' +show.releaseDate+')'}</span>
  );

}