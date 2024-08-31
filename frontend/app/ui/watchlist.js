"use client";
import { useCallback, useEffect, useState } from "react";
import { fetchShows } from "../lib/data";
import TileWatchlist from "./tileWatchlist";
import Notification, { hide } from "./notification";

export default function WatchlistFiltered({ type }) {
  const [shows, setShows] = useState([]);
  const [message, setMessage] = useState(hide());

  const getShows = useCallback(async () => {
    let res = await fetchShows(type);
    setShows(res);
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
