import { Button, Modal, ModalBody, ModalFooter, ModalHeader, Toast, ToastBody, ToastHeader } from 'reactstrap';
import '../../App.css';
import Duration from '../../show/Duration';
import Genres from '../../show/Genres';
import Title from '../../show/Title';
import WatchProviders from '../../show/WatchProviders';
import { useState } from 'react';

export default function SearchResultDetails({ show, poster, modal, setModal, error, setError }) {

  function addToWatchlist() {
    fetch('/api/shows', {
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
      .then(data => {
        if (data.status === 400) {
          console.error(data.message);
          setError(data.message);
        } else {
          console.log('Show added:', data);
          setModal(!modal);
        }
      });
    
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
