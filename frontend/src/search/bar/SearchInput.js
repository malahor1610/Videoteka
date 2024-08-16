import { Input } from 'reactstrap';
import '../../App.css';

export default function SearchInput({title, setTitle}) {

  return (
    <Input
      className='w-auto col-auto'
      value={title}
      placeholder="Search for title..."
      onChange={(e) => setTitle(e.target.value)} />
  );
}
