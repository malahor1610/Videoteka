"use client";
import { useCallback, useEffect, useState } from "react";
import { Button, Col, Row } from "reactstrap";
import { fetchShows } from "../lib/data";
import TileRandom from "../ui/tileRandom";
import Type from "../ui/type";

export default function Random() {
  const [shows, setShows] = useState([]);
  const [type, setType] = useState("MOVIE");

  const getShows = useCallback(async () => {
    let res = await fetchShows(type);
    setShows(res._embedded.shows);
  }, [type, setShows]);

  useEffect(() => {
    getShows();
  }, [getShows]);

  function handleSubmit() {
    const result = Array.from(shows);
    const random = result[Math.floor(Math.random() * result.length)];
    setShows(Array.of(random));
  }

  function excludeShow(e, show) {
    const result = Array.from(shows);
    result.splice(show, 1);
    setShows(result);
  }

  const pickBar = (
    <Row className="justify-content-center my-3">
      <Type type={type} setType={setType} />
      <Button
        color="primary"
        className="col-auto mx-3 my-1"
        disabled={shows.length === 1}
        onClick={handleSubmit}
      >
        Wylosuj
      </Button>
      <Button color="danger" className="col-auto mx-3 my-1" onClick={getShows}>
        Resetuj
      </Button>
    </Row>
  );

  const pickedRandom = (
    <Col xs="12" className="my-2">
      <TileRandom show={shows[0]} random />
    </Col>
  );

  const pickOptions = (
    <Row>
      {shows.map((show, index) => (
        <Col xs="6" sm="6" md="4" lg="3" xxl="2" className="my-2" key={show.id}>
          <TileRandom show={show} random disabled />
          <Row className="my-1 justify-content-center">
            <Button
              className="bg-danger btn-close"
              onClick={(e) => excludeShow(e, index)}
            />
          </Row>
        </Col>
      ))}
    </Row>
  );

  return shows.length === 1 ? (
    <>
      {pickBar}
      {pickedRandom}
    </>
  ) : (
    <>
      {pickBar}
      {pickOptions}
    </>
  );
}
