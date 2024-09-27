"use client";
import { useContext, useState } from "react";
import { Button, Col, Container, Form, Input, Row } from "reactstrap";
import Type from "../ui/type";
import { fetchSearch } from "../lib/data";
import TileSearch from "../ui/tileSearch";
import Notification, { error, hide } from "../ui/notification";
import { LoadingContext } from "../layout";

export default function Search() {
  const [shows, setShows] = useState([]);
  const [title, setTitle] = useState("");
  const [type, setType] = useState("MOVIE");
  const [message, setMessage] = useState(hide());
  const { loading, setLoading } = useContext(LoadingContext);

  async function search(e) {
    e.preventDefault();
    setLoading(true);
    let result = await fetchSearch(title, type);
    if (result.error) setMessage(error(result.message));
    else setShows(result);
    setLoading(false);
  }

  return (
    <>
      <Form onSubmit={search}>
        <Row className="justify-content-center my-3">
          <Type type={type} setType={setType} />
          <Input
            className="my-1 w-auto col-auto rounded-0 rounded-start"
            value={title}
            placeholder="Szukaj tytułu..."
            onChange={(e) => setTitle(e.target.value)}
          />
          <Button
            color="light"
            type="button"
            onClick={() => setTitle("")}
            className="col-auto mx-0 my-1 p-0 rounded-0 rounded-end"
          >
            <i className="bi bi-eraser-fill dark p-2 fs-6"></i>
          </Button>
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
