"use client";
import { useCallback, useContext, useEffect, useState } from "react";
import { fetchShows } from "../lib/data";
import TileWatchlist from "./tileWatchlist";
import Notification, { hide } from "./notification";
import { LoadingContext } from "../layout";

export default function WatchlistFiltered({ type }) {
  const [shows, setShows] = useState([]);
  const [message, setMessage] = useState(hide());
  const { loading, setLoading } = useContext(LoadingContext);

  const getShows = useCallback(async () => {
    setLoading(true);
    let res = await fetchShows(type);
    setShows(res);
    setLoading(false);
  }, [type, setShows]);

  useEffect(() => {
    getShows();
  }, [getShows]);

  return (
    <div className="w-100 p-0">
      {shows.map((show) => (
        <TileWatchlist
          key={show.id}
          show={show}
          fetchShows={getShows}
          setMessage={setMessage}
        />
      ))}
      <Notification
        message={message.message}
        type={message.type}
        setMessage={setMessage}
      />
    </div>
  );
}
