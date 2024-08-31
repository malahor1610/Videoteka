"use client";
import {
  closestCorners,
  DndContext,
  PointerSensor,
  TouchSensor,
  useSensor,
  useSensors,
} from "@dnd-kit/core";
import {
  arrayMove,
  SortableContext,
  verticalListSortingStrategy,
} from "@dnd-kit/sortable";
import { useCallback, useEffect, useState } from "react";
import { fetchAllShows, updateShows } from "../lib/data";
import Notification, { hide } from "../ui/notification";
import TileWatchlist from "../ui/tileWatchlist";

export default function Watchlist() {
  const [shows, setShows] = useState([]);
  const [message, setMessage] = useState(hide());

  const getShows = useCallback(async () => {
    let res = await fetchAllShows();
    setShows(res._embedded.shows);
  }, []);

  useEffect(() => {
    getShows();
  }, [getShows]);

  async function updatePositions() {
    let res = await updateShows(shows);
    setShows(res);
  }

  function handleDragEnd(event) {
    const { active, over } = event;
    if (active.id !== over.id) {
      const oldIndex = shows.indexOf(
        shows.filter((show) => show.id === active.id)[0]
      );
      const newIndex = shows.indexOf(
        shows.filter((show) => show.id === over.id)[0]
      );
      let list = arrayMove(shows, oldIndex, newIndex);
      list.forEach((element) => {
        element.position = list.indexOf(element) + 1;
      });
      setShows(list);
      updatePositions();
    }
  }

  const sensors = useSensors(useSensor(PointerSensor), useSensor(TouchSensor));

  return (
    <>
      <DndContext
        sensors={sensors}
        collisionDetection={closestCorners}
        onDragEnd={handleDragEnd}
      >
        <SortableContext items={shows} strategy={verticalListSortingStrategy}>
          {shows.map((show) => (
            <TileWatchlist
              key={show.id}
              id={show.id}
              show={show}
              orderable
              fetchShows={getShows}
              setMessage={setMessage}
            />
          ))}
        </SortableContext>
      </DndContext>
      <Notification
        message={message.message}
        type={message.type}
        setMessage={setMessage}
      />
    </>
  );
}
