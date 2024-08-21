import { Button, Form, Input } from 'reactstrap';
import '../App.css';
import Type from '../show/Type';
import { useState } from 'react';

export default function SearchBar({ setShows }) {
  const [title, setTitle] = useState('');
  const [type, setType] = useState('MOVIE');

  function search(e) {
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
    <Form className='row justify-content-center my-3' onSubmit={search}>
      <Type type={type} setType={setType} />
      <Input
        className='w-auto col-auto'
        value={title}
        placeholder="Search for title..."
        onChange={(e) => setTitle(e.target.value)} />
      <Button color="primary" className='col-auto mx-3'>Search</Button>
    </Form>
  );
}
