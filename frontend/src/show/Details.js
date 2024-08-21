import { Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import '../App.css';
import Duration from './Duration';
import Genres from './Genres';
import Title from './Title';
import WatchProviders from './WatchProviders';

export default function Details({ show, modal, setModal, button }) {

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
      {button ? (<ModalFooter>{button}</ModalFooter>) : (<></>)}
    </Modal>
  );
}
