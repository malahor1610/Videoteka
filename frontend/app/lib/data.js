export async function postShow(details, poster) {
  let res = await fetch("/api/shows", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: details.id,
      title: details.title,
      originalTitle: details.originalTitle,
      releaseDate: details.releaseDate,
      poster: poster,
      duration: details.duration,
      type: details.type,
    }),
  });
  return await res.json();
}

export async function updateShows(list) {
  let res = await fetch("/api/shows/positions", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(list),
  });
  return await res.json();
}

export async function deleteShow(id) {
  let res = await fetch("/api/shows/" + id, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  });
  return await res.json();
}

export async function fetchAllShows() {
  const searchParams = new URLSearchParams({
    sort: "position",
  }).toString();
  let res = await fetch("/api/shows?" + searchParams);
  return await res.json();
}

export async function fetchShows(type) {
  const searchParams = new URLSearchParams({
    type: type,
  }).toString();
  let res = await fetch("/api/shows/search/byType?" + searchParams);
  return await res.json();
}

export async function fetchSearch(title, type) {
  const searchParams = new URLSearchParams({
    title: title,
    type: type,
  }).toString();
  let res = await fetch("/api/search?" + searchParams);
  return await res.json();
}

export async function fetchDetails(show) {
  const searchParams = new URLSearchParams({
    type: show.type,
  }).toString();
  let res = await fetch("/api/search/" + show.id + "?" + searchParams);
  return await res.json();
}
