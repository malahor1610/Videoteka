import { Badge, Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import '../../App.css';
import WatchProviders from '../../show/WatchProviders';
import Duration from '../../show/Duration';
import Title from '../../show/Title';
import Genres from '../../show/Genres';

export default function SearchResultDetails({ show, poster, modal, setModal }) {

  function addToWatchlist() {
    fetch('/shows', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        id: show.id,
        title: show.title,
        originalTitle: show.originalTitle,
        releaseDate: show.releaseDate,
        poster: poster,
        duration: show.duration,
        type: show.type
      }),
    })
      .then(response => response.json())
      .then(data => console.log('Show added:', data))
      .catch(error => console.error('Error adding show:', error));
    setModal(!modal);
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
        <Button color="primary" onClick={() => addToWatchlist()}>
          Add to watchlist
        </Button>
      </ModalFooter>
    </Modal>
  );
}
