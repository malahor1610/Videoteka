import { useState } from 'react';
import { Button } from 'reactstrap';
import '../../App.css';
import { error, success } from '../../shell/Notification';
import Details from '../Details';
import TileRandom from './TileRandom';
import TileSearch from './TileSearch';
import TileWatchlist from './TileWatchlist';

export default function Tile({ show, search, random, disabled, orderable, setMessage, fetchShows }) {

  const [modal, setModal] = useState(false);
  const [details, setDetails] = useState([]);

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
      .then(result => result.json())
      .then(result => {
        if (result.status >= 400) setMessage(error(result.message));
        else setMessage(success('Show added successfully'))
        setModal(!modal);
      });
  }

  function removeFromWatchlist() {
    fetch('/api/shows/' + show.id, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      }
    })
      .then(result => result.json())
      .then(result => {
        setMessage(success('Show removed from watchlist'));
        setModal(!modal);
        fetchShows();
      });
  }

  const addButton = (
    <Button color="primary" onClick={addToWatchlist}>
      Add to watchlist
    </Button>
  );

  const removeButton = (
    <Button color="primary" onClick={removeFromWatchlist}>
      Remove from watchlist
    </Button>
  );

  if (search) return (<>
    <TileSearch show={show} openModal={openModal} />
    <Details show={details} modal={modal} setModal={setModal} button={addButton} />
  </>)
  else if (random) return (<>
    <TileRandom show={show} openModal={openModal} disabled={disabled} />
    <Details show={details} modal={modal} setModal={setModal} />
  </>)
  else return (<>
    <TileWatchlist show={show} openModal={openModal} orderable={orderable} />
    <Details show={details} modal={modal} setModal={setModal} button={removeButton} />
  </>
  );
}
