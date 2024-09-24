"use client";
import {
  closestCorners,
  DndContext,
  PointerSensor,
  useSensor,
  useSensors,
} from "@dnd-kit/core";
import {
  arrayMove,
  SortableContext,
  verticalListSortingStrategy,
} from "@dnd-kit/sortable";
import { useCallback, useContext, useEffect, useState } from "react";
import { LoadingContext } from "../layout";
import { fetchAllShows, updateShowsPositions } from "../lib/data";
import Changed from "../ui/changed";
import Notification, { hide } from "../ui/notification";
import TileWatchlist from "../ui/tileWatchlist";

export default function Watchlist() {
  const [shows, setShows] = useState([]);
  const [message, setMessage] = useState(hide());
  const [modal, setModal] = useState(false);
  const [lockedChanged, setLockedChanged] = useState([]);
  const { loading, setLoading } = useContext(LoadingContext);

  const openModal = useCallback(changed => {
    if (changed.length > 0) {
      setLockedChanged(changed);
      setModal(!modal);
    }
  }, [modal]);

  const getShows = useCallback(async () => {
    setLoading(true);
    let res = await fetchAllShows();
    let changed = res.filter((r) => r.showStatus?.indexOf("CHANGED") >= 0);
    openModal(changed);
    setShows(res);
    setLoading(false);
  }, [openModal, setLoading]);

  useEffect(() => {
    getShows();
  }, [getShows]);

  async function updatePositions() {
    setLoading(true);
    let res = await updateShowsPositions(shows);
    setShows(res);
    setLoading(false);
  }

  function moveToTheEdge(show, up) {
    let list = Array.from(shows);
    if(up) list.unshift(list.splice(list.indexOf(show), 1)[0]);
    else list.push(list.splice(list.indexOf(show), 1)[0]);
    list.forEach((element) => {
      element.position = list.indexOf(element) + 1;
    });
    setShows(list);
    updatePositions();
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

  const sensors = useSensors(useSensor(PointerSensor));

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
              moveShow={moveToTheEdge}
              setMessage={setMessage}
            />
          ))}
        </SortableContext>
      </DndContext>
      <Changed
        shows={lockedChanged}
        modal={modal}
        setModal={setModal}
        getShows={getShows}
      />
      <Notification
        message={message.message}
        type={message.type}
        setMessage={setMessage}
      />
    </>
  );
}
