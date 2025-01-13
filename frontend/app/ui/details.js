"use client";
import { useRouter } from "next/navigation";
import { useContext } from "react";
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";
import { LoadingContext } from "../layout";
import { unwatchShow, watchShow } from "../lib/data";
import Continuation from "./continuation";
import Duration from "./duration";
import Genres from "./genres";
import Loading from "./loading";
import Title from "./title";
import WatchProviders from "./watchProviders";

export default function Details({
  show,
  modal,
  setModal,
  button,
  buttons,
  collectionPart,
  onClose
}) {
  const { loading, setLoading } = useContext(LoadingContext);
  const router = useRouter();

  async function markUnwatched() {
    setLoading(true);
    let result = await unwatchShow(show);
    show.watchState = result.watchState;
    setLoading(false);
  }

  async function markWatched() {
    setLoading(true);
    let result = await watchShow(show);
    show.watchState = result.watchState;
    setLoading(false);
  }

  function goToCollection() {
    router.push(`/collection?id=${show.collection.id}`);
  }

  return (
    <Modal
      className="text-dark text-start fs-6"
      size="lg"
      isOpen={modal}
      toggle={() => setModal(!modal)}
      onClosed={onClose}
    >
      <ModalHeader toggle={() => setModal(!modal)}>
        <Title show={show} />
        <Duration
          duration={show.duration}
          seasons={show.seasons}
          type={show.showType}
        />
      </ModalHeader>
      <ModalBody>
        <Continuation continuation={show.continuation} showType={show.showType} />
        {show.overview}
        <Genres genres={show.genres} />
        <WatchProviders
          type="Dostępne:"
          providers={show.watchProviders?.available}
        />
        <WatchProviders
          type="Do wypożyczenia:"
          providers={show.watchProviders?.rent}
        />
        <WatchProviders
          type="Do kupienia:"
          providers={show.watchProviders?.buy}
        />
      </ModalBody>
      <ModalFooter>
        {show.watchState?.indexOf("WATCHED") === 0 ? (
          <Button color="secondary" onClick={markUnwatched}>
            Nieobejrzane
          </Button>
        ) : (
          <Button color="secondary" onClick={markWatched}>
            Obejrzane
          </Button>
        )}
        {show.collection && !collectionPart ? (
          <Button color="success" onClick={goToCollection}>
            Zobacz {show.collection.name}
          </Button>
        ) : (
          <></>
        )}
        {button}
        {buttons}
      </ModalFooter>
      <Loading loading={loading} details />
    </Modal>
  );
}
