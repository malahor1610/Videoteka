import { Button, Card, CardBody, CardTitle } from 'reactstrap';
import '../App.css';
import Poster from '../show/Poster';
import Title from '../show/Title';
import Details from '../show/Details';
import { useState } from 'react';

export default function SearchResult({ show, setError }) {

  const [details, setDetails] = useState([]);
  const [modal, setModal] = useState(false);

  function openModal() {
    const searchParams = new URLSearchParams({
      type: show.type
    }).toString();
    fetch("/api/search/" + show.id + "?" + searchParams)
      .then(result => result.json())
      .then(result => setDetails(result));
    setModal(!modal);
  }

  function addToWatchlist() {
    fetch('/api/shows', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        id: details.id,
        title: details.title,
        originalTitle: details.originalTitle,
        releaseDate: details.releaseDate,
        poster: show.poster,
        duration: details.duration,
        type: details.type
      }),
    })
      .then(response => response.json())
      .then(data => {
        if (data.status >= 400) {
          setError(data.message);
        }
        setModal(!modal);
      });
  }

  const addButton = (
    <Button color="primary" onClick={addToWatchlist}>
      Add to watchlist
    </Button>
  );

  return (
    <Card className='mx-1' color="dark" inverse>
      <Button className='' onClick={openModal}>
        <CardBody className='p-1'>
          <Poster image={show.poster} />
          <CardTitle className='m-0' tag="h6">
            <Title show={show} />
          </CardTitle>
        </CardBody>
      </Button>
      <Details show={details} modal={modal} setModal={setModal} button={addButton} />
    </Card>
  );
}
