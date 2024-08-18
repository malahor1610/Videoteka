import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import '../App.css';
import Genres from '../show/Genres';
import Title from '../show/Title';
import WatchProviders from '../show/WatchProviders';
import Duration from '../show/Duration';

export default function WatchlistElementDetails({ show, modal, setModal }) {

  function removeFromWatchlist() {
    fetch('/api/shows/' + show.id, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      }
    })
      .then(response => response.json())
      .then(data => console.log('Show removed:', data))
      .catch(error => console.error('Error removing show:', error));
    setModal(!modal);
    window.location.reload();
  }

  return (
    <Modal size='lg' isOpen={modal} toggle={() => setModal(!modal)}>
      <ModalHeader toggle={() => setModal(!modal)}>
        <Title show={show} />
        <Duration duration={show.duration} />
      </ModalHeader>
      <ModalBody>
        {show.overview}
        <Genres genres={show.genres} />
        <WatchProviders type="Available on:" providers={show.watchProviders?.available} />
        <WatchProviders type="To rent on:" providers={show.watchProviders?.rent} />
        <WatchProviders type="To buy on:" providers={show.watchProviders?.buy} />
      </ModalBody>
      <ModalFooter>
        <Button color="primary" onClick={() => removeFromWatchlist()}>
          Remove from watchlist
        </Button>
      </ModalFooter>
    </Modal>
  );
}
