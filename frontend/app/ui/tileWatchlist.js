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
import { deleteShow, fetchDetails } from "../lib/data";
import Details from "./details";
import Duration from "./duration";
import Poster from "./poster";
import Title from "./title";
import { useContext, useState } from "react";
import { useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { success } from "./notification";
import { LoadingContext } from "../layout";

export default function TileWatchlist({ id, show, orderable, fetchShows, setMessage }) {
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

  async function removeFromWatchlist() {
    setLoading(true);
    let result = await deleteShow(show);
    setMessage(success('Usunięto pozycję z listy'));
    setModal(!modal);
    fetchShows();
    setLoading(false);
  }

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
    <Col
      style={{touchAction: 'none'}}
      {...listeners}
      {...attributes}
      xs="1"
      className="px-1 align-content-center border-start border-secondary border-2 rounded"
    >
      <i className="bi bi-grip-vertical d-flex d-sm-none justify-content-center fs-6 text-secondary"></i>
      <i className="bi bi-grip-vertical d-none d-sm-flex justify-content-center fs-4 text-secondary"></i>
    </Col>
  );

  return (
    <div ref={setNodeRef} style={style}>
      <Container className="px-1 mt-2 mb-2" style={{ maxWidth: "666px" }}>
        <Card className="px-1" color="dark" inverse>
          <Row className="px-1">
            <Col xs="4" sm="3" className="p-0">
              <Poster image={show.poster} />
            </Col>
            <Button
              className="col opacity-100"
              color="dark"
              onClick={openModal}
            >
              <CardBody className="p-0">
                <CardTitle className="d-block d-sm-none h6">
                  <Title show={show} />
                </CardTitle>
                <CardTitle className="d-none d-sm-block h4">
                  <Title show={show} />
                </CardTitle>
                <CardSubtitle>
                  <Duration duration={show.duration} />
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
        button={removeButton}
      />
    </div>
  );
}
