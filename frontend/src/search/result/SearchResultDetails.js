import { Badge, Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import '../../App.css';
import SearchResultDetailsProvider from './SearchResultDetailsProvider';

export default function SearchResultDetails({ show, poster, modal, setModal }) {

  function addToWatchlist() {
    fetch('/videos', {
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
        type: show.type
      }),
    })
    .then(response => response.json())
    .then(data => console.log('Show added:', data))
    .catch(error => console.error('Error adding show:', error));
    setModal(!modal);
  }

  return (
    <Modal size='lg'
      isOpen={modal}
      toggle={() => setModal(!modal)}
    >
      <ModalHeader toggle={() => setModal(!modal)}>{show.title} ({show.originalTitle})</ModalHeader>
      <ModalBody>
        {show.overview}
        <p>
          {show.genres?.map(genre =>
          <Badge className='me-1'>{genre}</Badge>
        )}
        </p>
        <SearchResultDetailsProvider text="Available on:" providers={show.watchProviders?.flatrate} />
        <SearchResultDetailsProvider text="To rent on:" providers={show.watchProviders?.rent} />
        <SearchResultDetailsProvider text="To buy on:" providers={show.watchProviders?.buy} />
      </ModalBody>
      <ModalFooter>
        <Button color="primary" onClick={() => setModal(!modal)}>
          Add to watchlist
        </Button>
      </ModalFooter>
    </Modal>
  );
}
