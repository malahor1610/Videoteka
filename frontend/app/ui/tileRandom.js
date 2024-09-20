import { useContext, useState } from "react";
import { Button } from "reactstrap";
import { fetchDetails } from "../lib/data";
import Details from "./details";
import Poster from "./poster";
import Title from "./title";
import { LoadingContext } from "../layout";

export default function TileRandom({ show, disabled, excluded }) {
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

  return (
    <>
      <Button
        className={excluded ? "opacity-25 p-0 p-sm-2" : "opacity-100 p-0 p-sm-2"}
        disabled={disabled}
        onClick={openModal}
      >
        <Poster image={show.poster} />
        <Title show={show} short />
      </Button>
      <Details show={details} modal={modal} setModal={setModal} />
    </>
  );
}
