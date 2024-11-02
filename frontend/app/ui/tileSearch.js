import { useContext, useState } from "react";
import { Button, Card, CardBody, CardTitle } from "reactstrap";
import { fetchDetails, postShow } from "../lib/data";
import Details from "./details";
import { error, success } from "./notification";
import Poster from "./poster";
import Title from "./title";
import { LoadingContext } from "../layout";

export default function TileSearch({ show, setMessage, onCloseModal }) {
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
    else setMessage(success('Pomyślnie dodano do listy'))
    setModal(!modal);
    setLoading(false);
  }

  const addButton = (
    <Button color="primary" onClick={addToWatchlist} disabled={details.watchState === "WATCHED_ON_LIST" || details.watchState === "UNWATCHED"}>
      {details.watchState === "WATCHED_ON_LIST" || details.watchState === "UNWATCHED" ? "Pozycja na liście" : "Dodaj do listy"}
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
        onClose={onCloseModal}
      />
    </>
  );
}
