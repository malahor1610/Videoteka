import { useState } from 'react';
import { Button, Form, Input } from 'reactstrap';
import '../App.css';
import Bar from '../shell/Bar';
import Type from '../show/Type';

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
    <Form onSubmit={search}>
      <Bar content={(<>
        <Type type={type} setType={setType} />
        <Input
          className='w-auto col-auto'
          value={title}
          placeholder="Search for title..."
          onChange={(e) => setTitle(e.target.value)} />
        <Button color="primary" className='col-auto mx-3'>Search</Button>
      </>)} />
    </Form>
  );
}
