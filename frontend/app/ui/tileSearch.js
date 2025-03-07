import { useContext, useState } from "react";
import { Button, Card, CardBody, CardTitle } from "reactstrap";
import { LoadingContext } from "../layout";
import { fetchDetails, postShow } from "../lib/data";
import Details from "./details";
import ActionIcon from "./icon/actionIcon";
import { error, success } from "./notification";
import Poster from "./poster";
import Title from "./title";

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
    else setMessage(success("Pomyślnie dodano do listy"));
    setModal(!modal);
    setLoading(false);
  }

  const addButton =
    details.watchState === "WATCHED_ON_LIST" ||
    details.watchState === "UNWATCHED" ? (
      <ActionIcon type="disabledAdd" />
    ) : (
      <ActionIcon onClick={addToWatchlist} type="add" />
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
