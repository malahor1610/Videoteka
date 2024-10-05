"use client";
import { useCallback, useContext, useEffect, useState } from "react";
import { Col, Row } from "reactstrap";
import { LoadingContext } from "../layout";
import { fetchWatched } from "../lib/data";
import Type from "../ui/type";
import TileSearch from "../ui/tileSearch";
import Notification, { hide } from "../ui/notification";

export default function Watched() {
  const [shows, setShows] = useState([]);
  const [type, setType] = useState("MOVIE");
  const [message, setMessage] = useState(hide());
  const { loading, setLoading } = useContext(LoadingContext);

  const getShows = useCallback(async () => {
    setLoading(true);
    let res = await fetchWatched(type);
    setShows(res);
    setLoading(false);
  }, [type, setShows, setLoading]);

  useEffect(() => {
    getShows();
  }, [getShows]);

  async function onCloseModal() {
    await getShows();
  }


  return (
    <>
      <Row className="justify-content-center my-3">
        <Type type={type} setType={setType} />
      </Row>
      <Row className="text-center">
        {shows.map((show) => (
          <Col
            xs="6"
            sm="6"
            md="4"
            lg="3"
            xxl="2"
            className="my-2"
            key={show.id}
          >
            <TileSearch show={show} setMessage={setMessage} onCloseModal={onCloseModal} />
          </Col>
        ))}
      </Row>
      <Notification
        message={message.message}
        type={message.type}
        setMessage={setMessage}
      />
    </>
  );
}
