"use client";

import { useEffect, useState } from "react";
import { Input, Row } from "reactstrap";

export default function TitleFilter({ allShows, setShows }) {
  const [title, setTitle] = useState("");

  function filterShows() {
    let filtered = Array.from(allShows);
    filtered = filtered.filter((show) => show.title.toLowerCase().startsWith(title.toLowerCase()));
    setShows(filtered);
  }

  useEffect(() => {
    if (title === "") setShows(allShows);
    else filterShows();
  }, [title]);

  return (
    <Row className="justify-content-center my-3">
      <Input
        type="search"
        className="my-1 w-auto col-auto"
        value={title}
        placeholder="Szukaj tytułu..."
        onChange={(e) => setTitle(e.target.value)}
      />
    </Row>
  );
}
