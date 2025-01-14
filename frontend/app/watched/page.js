"use client";
import { useCallback, useContext, useEffect, useState } from "react";
import { Col, Row } from "reactstrap";
import { LoadingContext } from "../layout";
import { fetchWatched } from "../lib/data";
import Type from "../ui/type";
import TileSearch from "../ui/tileSearch";
import Notification, { hide } from "../ui/notification";
import { useRouter, useSearchParams } from "next/navigation";

export default function Watched() {
  const searchParams = useSearchParams();
  const typeFromUrl = searchParams.get("type") || "MOVIE";
  const [shows, setShows] = useState([]);
  const [type, setType] = useState(typeFromUrl);
  const [message, setMessage] = useState(hide());
  const { loading, setLoading } = useContext(LoadingContext);
  const router = useRouter();

  const getShows = useCallback(async (typeFromUrl) => {
    setLoading(true);
    router.push(`?type=${typeFromUrl}`);
    let res = await fetchWatched(typeFromUrl);
    setShows(res);
    setLoading(false);
  }, [setShows, setLoading]);

  useEffect(() => {
    setType(typeFromUrl);
    getShows(typeFromUrl);
  }, [getShows, typeFromUrl]);

  useEffect(() => {
    getShows(type);
  }, [type]);

  async function onCloseModal() {
    await getShows(typeFromUrl);
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
