import { useState } from "react";
import { Button } from "reactstrap";
import { fetchDetails } from "../lib/data";
import Details from "./details";
import Poster from "./poster";
import Title from "./title";

export default function TileRandom({ show, disabled }) {
  const [modal, setModal] = useState(false);
  const [details, setDetails] = useState([]);

  async function openModal() {
    let result = await fetchDetails(show);
    setDetails(result);
    setModal(!modal);
  }

  return (
    <>
      <Button
        className="opacity-100 p-0 p-sm-2"
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
