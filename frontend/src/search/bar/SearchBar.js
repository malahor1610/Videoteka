import { useState } from 'react';
import { Form } from 'reactstrap';
import '../../App.css';
import Type from '../../show/Type';
import SearchButton from './SearchButton';
import SearchInput from './SearchInput';

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
      <Type type={type} setType={setType} />
      <SearchInput title={title} setTitle={setTitle} />
      <SearchButton/>
    </Form>
  );
}
