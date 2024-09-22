"use client";
import { useContext } from "react";
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";
import { LoadingContext } from "../layout";
import Duration from "./duration";
import Genres from "./genres";
import Loading from "./loading";
import Title from "./title";
import WatchProviders from "./watchProviders";
import { redirect, useRouter, useSearchParams } from "next/navigation";

export default function Details({ show, modal, setModal, button, buttons, collectionPart }) {
  const { loading, setLoading } = useContext(LoadingContext);
  const router = useRouter();

  function goToCollection() {
    router.push(`/collection?id=${show.collection.id}`);
  }

  return (
    <Modal
      className="text-dark text-start fs-6"
      size="lg"
      isOpen={modal}
      toggle={() => setModal(!modal)}
    >
      <ModalHeader toggle={() => setModal(!modal)}>
        <Title show={show} />
        <Duration duration={show.duration} />
      </ModalHeader>
      <ModalBody>
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
