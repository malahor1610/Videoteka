"use client";
import { useCallback, useContext, useEffect, useState } from "react";
import { Button, Col, Row } from "reactstrap";
import { LoadingContext } from "../layout";
import { fetchReleased } from "../lib/data";
import GenresFilters from "../ui/genresFilter";
import TileRandom from "../ui/tileRandom";
import Type from "../ui/type";
import { useRouter, useSearchParams } from "next/navigation";

export default function Random() {
  const searchParams = useSearchParams();
  const typeFromUrl = searchParams.get("type") || "MOVIE";
  const [shows, setShows] = useState([]);
  const [allShows, setAllShows] = useState([]);
  const [excluded, setExcluded] = useState([]);
  const [type, setType] = useState(typeFromUrl);
  const [genres, setGenres] = useState([]);
  const { loading, setLoading } = useContext(LoadingContext);
  const router = useRouter();

  const getShows = useCallback(
    async (typeFromUrl) => {
      setLoading(true);
      router.push(`?type=${typeFromUrl}`);
      let res = await fetchReleased(typeFromUrl);
      setShows(res);
      setAllShows(res);
      setExcluded([]);
      setGenres(
        [...new Set(res.map((r) => r.genres).flat())]
          .sort()
          .map((genre) => ({ name: genre, active: false, excluded: false }))
      );
      setLoading(false);
    },
    [setShows, setLoading]
  );

  useEffect(() => {
    setType(typeFromUrl);
    getShows(typeFromUrl);
  }, [getShows, typeFromUrl]);

  useEffect(() => {
    getShows(type);
  }, [type]);

  function handleSubmit() {
    const result = Array.from(shows);
    const random = result[Math.floor(Math.random() * result.length)];
    setShows(Array.of(random));
  }

  function excludeShow(e, show) {
    const result = Array.from(shows);
    const excludedShows = Array.from(excluded);
    excludedShows.push(result.splice(show, 1)[0]);
    setExcluded(excludedShows);
    setShows(result);
  }

  function includeShow(e, show) {
    const result = Array.from(shows);
    const excludedShows = Array.from(excluded);
    result.push(excludedShows.splice(show, 1)[0]);
    setExcluded(excludedShows);
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
      <Button
        color="danger"
        className="col-auto mx-3 my-1"
        onClick={() => getShows(type)}
      >
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
              className="btn btn-link link-danger col-auto p-0"
              onClick={(e) => excludeShow(e, index)}
            >
              <i className="bi bi-x-square-fill danger p-2 fs-4"></i>
            </Button>
          </Row>
        </Col>
      ))}
      {excluded.map((ex, index) => (
        <Col xs="6" sm="6" md="4" lg="3" xxl="2" className="my-2" key={ex.id}>
          <TileRandom show={ex} random disabled excluded />
          <Row className="w-auto my-1 justify-content-center">
            <Button
              className="btn btn-link link-success col-auto p-0"
              onClick={(e) => includeShow(e, index)}
            >
              <i className="bi bi-check-square-fill success p-2 fs-4"></i>
            </Button>
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
      <GenresFilters
        allShows={allShows}
        setShows={setShows}
        setExcluded={setExcluded}
        genres={genres}
        setGenres={setGenres}
      />
      {pickOptions}
    </>
  );
}
