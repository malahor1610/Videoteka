import { useState } from 'react';
import '../App.css';
import Notification, { hide } from '../shell/Notification';
import Page from '../shell/Page';
import SearchBar from './SearchBar';
import SearchResults from './SearchResults';

export default function Search() {
  const [shows, setShows] = useState([]);
  const [message, setMessage] = useState(hide());

  return (
    <Page content={(<>
      <SearchBar setShows={setShows} setMessage={setMessage} />
      <SearchResults shows={shows} setMessage={setMessage} />
      <Notification message={message.message} type={message.type} setMessage={setMessage} />
    </>
    )} />
  );
}
