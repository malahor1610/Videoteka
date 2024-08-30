import { Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';
import Duration from './duration';
import Genres from './genres';
import Title from './title';
import WatchProviders from './watchProviders';

export default function Details({ show, modal, setModal, button }) {

  return (
    <Modal className='text-dark text-start fs-6' size='lg' isOpen={modal} toggle={() => setModal(!modal)}>
      <ModalHeader toggle={() => setModal(!modal)}>
        <Title show={show} />
        <Duration duration={show.duration} />
      </ModalHeader>
      <ModalBody>
        {show.overview}
        <Genres genres={show.genres} />
        <WatchProviders type="Dostępne:" providers={show.watchProviders?.available} />
        <WatchProviders type="Do wypożyczenia:" providers={show.watchProviders?.rent} />
        <WatchProviders type="Do kupienia:" providers={show.watchProviders?.buy} />
      </ModalBody>
      {button ? (<ModalFooter>{button}</ModalFooter>) : (<></>)}
    </Modal>
  );
}