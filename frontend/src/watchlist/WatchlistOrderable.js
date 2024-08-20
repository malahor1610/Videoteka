import { DragDropContext, Draggable, Droppable } from 'react-beautiful-dnd';
import '../App.css';
import WatchlistElement from './WatchlistElement';

export default function WatchlistOrderable({ shows, setOrder }) {

  function handleDragEnd(destination, source) {
    if (!destination) return;
    let list = reorder(shows, source.index, destination.index);
    list.forEach(element => {
      element.position = list.indexOf(element) + 1;
    });
    setOrder(list);
  };

  function reorder(list, startIndex, endIndex) {
    const result = Array.from(list);
    const [removed] = result.splice(startIndex, 1);
    result.splice(endIndex, 0, removed);
    return result;
  };

  return (
    <DragDropContext onDragEnd={({ destination, source }) => handleDragEnd(destination, source)}>
      <Droppable droppableId="droppable">
        {(provided) => (
          <div className='w-100' ref={provided.innerRef} {...provided.droppableProps}>
            <div className='mt-5 pt-5 w-100'>
              {shows.map((show, index) =>
                <Draggable
                  key={show.position}
                  index={index}
                  draggableId={'drag-' + show.position}
                >
                  {(provided, snapshot) => (
                    <div
                      ref={provided.innerRef}
                      {...provided.draggableProps}
                      {...provided.dragHandleProps}
                    >
                      <WatchlistElement show={show} orderable />
                    </div>
                  )}
                </Draggable>
              )}
            </div>
          </div>
        )}
      </Droppable>
    </DragDropContext>
  );
}
