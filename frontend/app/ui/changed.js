"use client";
import { useContext, useEffect, useState } from "react";
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from "reactstrap";
import { LoadingContext } from "../layout";
import ChangedSet from "./changedSet";
import Loading from "./loading";
import { updateShowsLocks } from "../lib/data";

export default function Changed({ shows, modal, setModal, getShows }) {
  const { loading, setLoading } = useContext(LoadingContext);


  async function acknowledge(){
    await updateShowsLocks(shows);
    setModal(!modal);
    getShows();
  }

  return (
    <Modal
      className="text-dark text-start fs-6"
      backdrop="false"
      isOpen={modal}
    >
      <ModalHeader>
        Info o serialach, które oglądasz
      </ModalHeader>
      <ModalBody>
        <ChangedSet shows={shows.filter((show) => show.showStatus === "LOCKED_CHANGED_CANCELED")} text="Seriale anulowane: " />
        <ChangedSet shows={shows.filter((show) => show.showStatus === "LOCKED_CHANGED_IN_PRODUCTION")} text="Seriale w produkcji: " />
        <ChangedSet shows={shows.filter((show) => show.showStatus === "LOCKED_CHANGED_DATE")} text="Seriale z potwierdzoną datą: " />
        <ChangedSet shows={shows.filter((show) => show.showStatus === "LOCKED_CHANGED_RELEASED")} text="Seriale już wydane: " />
      </ModalBody>
      <ModalFooter>
        <Button color="success" onClick={acknowledge}>
          Ok
        </Button>
      </ModalFooter>
      <Loading loading={loading} details />
    </Modal>
  );
}
