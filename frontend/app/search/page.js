"use client";
import { useState } from "react";
import { Button, Col, Container, Form, Input, Row } from "reactstrap";
import Type from "../ui/type";
import { fetchSearch } from "../lib/data";
import TileSearch from "../ui/tileSearch";

export default function Search() {
  const [shows, setShows] = useState([]);
  const [title, setTitle] = useState("");
  const [type, setType] = useState("MOVIE");

  async function search(e) {
    e.preventDefault();
    let result = await fetchSearch(title, type);
    setShows(result);
  }

  return (
    <>
      <Form onSubmit={search}>
        <Row className="justify-content-center my-3">
          <Type type={type} setType={setType} />
          <Input
            className="my-1 w-auto col-auto"
            value={title}
            placeholder="Szukaj tytułu..."
            onChange={(e) => setTitle(e.target.value)}
          />
          <Button color="primary" className="col-auto mx-3 my-1">
            Szukaj
          </Button>
        </Row>
      </Form>
      <Container className="row text-center">
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
            <TileSearch show={show} search />
          </Col>
        ))}
      </Container>
    </>
  );
}
