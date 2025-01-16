import { useContext, useState } from "react";
import {
  Button,
  Card,
  CardBody,
  CardTitle
} from "reactstrap";
import { LoadingContext } from "../layout";
import { fetchDetails, lockShow, unlockShow } from "../lib/data";
import Details from "./details";
import ActionIcon from "./icon/actionIcon";
import { success } from "./notification";
import Poster from "./poster";
import Title from "./title";

export default function TileWatched({ show, setMessage, onCloseModal }) {
  const [modal, setModal] = useState(false);
  const [details, setDetails] = useState([]);
  const { loading, setLoading } = useContext(LoadingContext);

  async function openModal() {
    setLoading(true);
    let result = await fetchDetails(show);
    setDetails(result);
    setModal(!modal);
    setLoading(false);
  }

  async function addToWatchlist() {
    setLoading(true);
    let result = await postShow(details);
    if (result.error) setMessage(error(result.message));
    else setMessage(success("Pomyślnie dodano do listy"));
    setModal(!modal);
    setLoading(false);
  }

  async function lock() {
    setLoading(true);
    let result = await lockShow(show);
    show.lockState = result.lockState;
    setMessage(success("Włączono powiadomienia o kontynuacji"));
    setModal(!modal);
    setLoading(false);
  }

  async function unlock() {
    setLoading(true);
    let result = await unlockShow(show);
    show.lockState = result.lockState;
    setMessage(success("Wyłączono powiadomienia"));
    setModal(!modal);
    setLoading(false);
  }

  async function updateLock(lockState) {
    show.lockState = lockState;
    setMessage(success("Włączono powiadomienia"));
  }

  const addButton =
    details.watchState === "WATCHED_ON_LIST" ||
    details.watchState === "UNWATCHED" ? (
      <ActionIcon key="disabledAddButton" type="disabledAdd" />
    ) : (
      <ActionIcon key="addButton" onClick={addToWatchlist} type="add" />
    );

  const lockButton =
    !show.lockState || show.lockState === "UNLOCKED" ? (
      <ActionIcon key="lockButton" onClick={lock} type="lock" />
    ) : show.lockState && show.lockState !== "UNLOCKED" ? (
      <ActionIcon key="unlockButton" onClick={unlock} type="unlock" />
    ) : (
      <></>
    );

  return (
    <>
      <Card color="dark" inverse>
        <Button className="opacity-100 p-0 p-sm-2" onClick={openModal}>
          <CardBody className="p-0 pb-1">
            <Poster image={show.poster} />
            <CardTitle className="m-0" tag="h6">
              <Title show={show} />
            </CardTitle>
          </CardBody>
        </Button>
      </Card>
      <Details
        show={details}
        modal={modal}
        setModal={setModal}
        buttons={[lockButton, addButton]}
        updateLock={updateLock}
        onClose={onCloseModal}
      />
    </>
  );
}
