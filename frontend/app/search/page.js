"use client";
import { useRouter, useSearchParams } from "next/navigation";
import { useContext, useEffect, useState } from "react";
import { Button, Col, Form, Input, Row } from "reactstrap";
import { LoadingContext } from "../layout";
import { fetchSearch } from "../lib/data";
import Notification, { error, hide } from "../ui/notification";
import TileSearch from "../ui/tileSearch";
import Type from "../ui/type";

export default function Search() {
  const searchParams = useSearchParams();
  const queryFromUrl = searchParams.get("query") || "";
  const typeFromUrl = searchParams.get("type") || "MOVIE";
  const [title, setTitle] = useState(queryFromUrl);
  const [shows, setShows] = useState([]);
  const [type, setType] = useState(typeFromUrl);
  const [message, setMessage] = useState(hide());
  const { loading, setLoading } = useContext(LoadingContext);
  const router = useRouter();

  async function search(e) {
    e.preventDefault();
    router.push(`?query=${title}&type=${type}`);
    fetchShows(title, type);
  }

  async function fetchShows(queryFromUrl, typeFromUrl) {
    setLoading(true);
    let result = await fetchSearch(queryFromUrl, typeFromUrl);
    if (result.error) setMessage(error(result.message));
    else setShows(result);
    setLoading(false);
  }

  useEffect(() => {
    setTitle(queryFromUrl);
    setType(typeFromUrl);
    if (queryFromUrl) {
      fetchShows(queryFromUrl, typeFromUrl);
    } else {
      setShows([]);
    }
  }, [queryFromUrl, typeFromUrl]);

  return (
    <>
      <Form onSubmit={search}>
        <Row className="justify-content-center my-3">
          <Type type={type} setType={setType} />
          <Row className="my-0 w-auto col-auto">
            <Input
              type="search"
              className="my-1 w-auto col-auto"
              value={title}
              placeholder="Szukaj tytułu..."
              onChange={(e) => setTitle(e.target.value)}
            />
          </Row>
          <Button color="primary" type="submit" className="col-auto mx-3 my-1">
            Szukaj
          </Button>
        </Row>
      </Form>
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
            <TileSearch show={show} setMessage={setMessage} />
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
