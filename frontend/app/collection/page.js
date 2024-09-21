"use client";
import { useSearchParams } from "next/navigation";
import { Suspense, useCallback, useContext, useEffect, useState } from "react";
import { Col, Row } from "reactstrap";
import { LoadingContext } from "../layout";
import { fetchSearchCollection } from "../lib/data";
import Notification, { error, hide } from "../ui/notification";
import TileCollection from "../ui/tileCollection";

export default function Suspended() {
  return (
    <Suspense>
      <Collection />
    </Suspense>
  );
}

export function Collection() {
  const [collection, setCollection] = useState({});
  const [message, setMessage] = useState(hide());
  const { loading, setLoading } = useContext(LoadingContext);
  const searchParams = useSearchParams();
  const id = searchParams.get("id");

  const getCollection = useCallback(async () => {
    setLoading(true);
    let result = await fetchSearchCollection(id);
    if (result.status === 400) setMessage(error(result.message));
    else setCollection(result);
    setLoading(false);
  }, []);

  useEffect(() => {
    getCollection();
  }, [getCollection]);

  return (
    <>
      <Row className="text-center">
        <h1 className="my-3">{collection.name}</h1>
        {collection?.parts?.map((show) => (
          <Col
            xs="6"
            sm="6"
            md="4"
            lg="3"
            xxl="2"
            className="my-2"
            key={show.id}
          >
            <TileCollection show={show} setMessage={setMessage} />
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
