import { Button, Card, CardBody, CardTitle } from "reactstrap";
import Poster from "./poster";
import Title from "./title";
import Details from "./details";
import { postShow, fetchDetails } from "../lib/data";
import { useState } from "react";

export default function TileSearch({ show }) {
  const [modal, setModal] = useState(false);
  const [details, setDetails] = useState([]);

  async function openModal() {
    let result = await fetchDetails(show);
    setDetails(result);
    setModal(!modal);
  }

  async function addToWatchlist() {
    let result = await postShow(show);
    setModal(!modal);
  }

  const addButton = (
    <Button color="primary" onClick={addToWatchlist}>
      Dodaj do listy
    </Button>
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
        button={addButton}
      />
    </>
  );
}
