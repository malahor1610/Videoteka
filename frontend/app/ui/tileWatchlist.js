import {
  Button,
  Card,
  CardBody,
  CardSubtitle,
  CardTitle,
  Col,
  Container,
  Row,
} from "reactstrap";
import { deleteShow, fetchDetails, lockShow, unlockShow } from "../lib/data";
import Details from "./details";
import Duration from "./duration";
import Poster from "./poster";
import Title from "./title";
import { useContext, useState } from "react";
import { useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { success } from "./notification";
import { LoadingContext } from "../layout";
import TileWatchlistIcon from "./tileWatchlistIcon";

export default function TileWatchlist({
  id,
  show,
  orderable,
  fetchShows,
  moveShow,
  setMessage,
}) {
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

  async function removeFromWatchlist() {
    setLoading(true);
    await deleteShow(show);
    setMessage(success("Usunięto pozycję z listy"));
    setModal(!modal);
    fetchShows();
    setLoading(false);
  }

  function moveTop() {
    moveShow(show, true);
  }

  function moveBottom() {
    moveShow(show, false);
  }

  const lockButton =
    show.showType === "SERIES" &&
    (!show.lockState || show.lockState === "UNLOCKED") ? (
      <Button color="secondary" onClick={lock}>
        Powiadom o kontynuacji
      </Button>
    ) : show.showType === "SERIES" &&
      show.lockState &&
      show.lockState !== "UNLOCKED" ? (
      <Button color="secondary" onClick={unlock}>
        Wyłącz powiadomienia
      </Button>
    ) : (
      <></>
    );

  const removeButton = (
    <Button color="primary" onClick={removeFromWatchlist}>
      Usuń z listy
    </Button>
  );

  const { attributes, listeners, setNodeRef, transform, transition } =
    useSortable({ id: id });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
  };

  const draggable = (
    <Col xs="1" className="px-1 border-start border-secondary border-2 rounded">
      <Row className="h-100">
        <Col className="align-content-start ps-1">
          <TileWatchlistIcon
            icon="bi-chevron-bar-up"
            isButton
            onClick={moveTop}
          />
        </Col>
        <Col
          style={{ touchAction: "none" }}
          {...listeners}
          {...attributes}
          className="align-content-center ps-2"
        >
          <TileWatchlistIcon icon="bi-grip-vertical" />
        </Col>
        <Col className="align-content-end ps-1">
          <TileWatchlistIcon
            icon="bi-chevron-bar-down"
            isButton
            onClick={moveBottom}
          />
        </Col>
      </Row>
    </Col>
  );

  return (
    <div ref={setNodeRef} style={style}>
      <Container className="px-1 mt-2 mb-2" style={{ maxWidth: "666px" }}>
        <Card className="px-1" color="dark" inverse>
          <Row
            className={
              !show.lockState || show.lockState === "UNLOCKED"
                ? "opacity-100 px-1"
                : "opacity-50 px-1"
            }
          >
            <Col xs="4" sm="3" className="p-0">
              <Poster image={show.poster} />
            </Col>
            <Button
              className="col opacity-100"
              color="dark"
              onClick={openModal}
            >
              <CardBody className="p-0">
                {!show.lockState || show.lockState === "UNLOCKED" ? (
                  <></>
                ) : (
                  <CardSubtitle>
                    <small>Włączono powiadomienia o kontynuacji</small>
                  </CardSubtitle>
                )}
                <CardTitle className="d-block d-sm-none h6">
                  <Title show={show} />
                </CardTitle>
                <CardTitle className="d-none d-sm-block h4">
                  <Title show={show} />
                </CardTitle>
                <CardSubtitle>
                  <Duration duration={show.duration} type={show.showType} />
                </CardSubtitle>
              </CardBody>
            </Button>
            {orderable ? draggable : <></>}
          </Row>
        </Card>
      </Container>
      <Details
        show={details}
        modal={modal}
        setModal={setModal}
        buttons={[lockButton, removeButton]}
      />
    </div>
  );
}
