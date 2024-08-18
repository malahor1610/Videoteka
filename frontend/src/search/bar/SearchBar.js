import { useState } from 'react';
import '../../App.css';
import SearchType from './SearchType';
import SearchInput from './SearchInput';
import SearchButton from './SearchButton';
import { Form } from 'reactstrap';

export default function SearchBar({ setShows }) {

  const [title, setTitle] = useState('');
  const [type, setType] = useState('MOVIE');

  function handleSubmit(e) {
    e.preventDefault();
    const searchParams = new URLSearchParams({
      title: title,
      type: type
    }).toString();
    fetch("/api/search?" + searchParams)
      .then(result => result.json())
      .then(result => setShows(result));
  }

  return (
    <Form className='row justify-content-center my-3' onSubmit={handleSubmit}>
      <SearchType type={type} setType={setType} />
      <SearchInput title={title} setTitle={setTitle} />
      <SearchButton/>
    </Form>
  );
}
