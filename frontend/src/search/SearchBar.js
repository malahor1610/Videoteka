import { useState } from 'react';
import { Button, Form, Input } from 'reactstrap';
import '../App.css';
import Bar from '../shell/Bar';
import { error } from '../shell/Notification';
import Type from '../show/Type';

export default function SearchBar({ setShows, setMessage }) {
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
      .then(result => {
        if (result.status >= 400) setMessage(error(result.message));
        else setShows(result);
      });
  }

  return (
    <Form onSubmit={search}>
      <Bar content={(<>
        <Type type={type} setType={setType} />
        <Input
          className='my-1 w-auto col-auto'
          value={title}
          placeholder="Search for title..."
          onChange={(e) => setTitle(e.target.value)} />
        <Button color="primary" className='col-auto mx-3 my-1'>Search</Button>
      </>)} />
    </Form>
  );
}
