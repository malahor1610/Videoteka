export async function postShow(details, poster) {
  let res = await fetch(process.env.NEXT_PUBLIC_HOST + "/api/shows", {
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
      showType: details.showType,
    }),
  });
  return await res.json();
}

export async function updateShows(list) {
  let res = await fetch(process.env.NEXT_PUBLIC_HOST + "/api/shows/positions", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(list),
  });
  return await res.json();
}

export async function deleteShow(show) {
  let res = await fetch(
    process.env.NEXT_PUBLIC_HOST + "/api/shows/" + show.id,
    {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    }
  );
  return await res.json();
}

export async function fetchAllShows() {
  const searchParams = new URLSearchParams({
    sort: "position",
  }).toString();
  let res = await fetch(
    process.env.NEXT_PUBLIC_HOST + "/api/shows?" + searchParams
  );
  res = await res.json();
  return res._embedded.shows;
}

export async function fetchShows(type) {
  const searchParams = new URLSearchParams({
    type: type,
  }).toString();
  let res = await fetch(
    process.env.NEXT_PUBLIC_HOST + "/api/shows/search/byType?" + searchParams
  );
  res = await res.json();
  return res._embedded.shows;
}

export async function fetchSearch(title, type) {
  const searchParams = new URLSearchParams({
    title: title,
    type: type,
  }).toString();
  let res = await fetch(
    process.env.NEXT_PUBLIC_HOST + "/api/search?" + searchParams
  );
  return await res.json();
}

export async function fetchDetails(show) {
  const searchParams = new URLSearchParams({
    type: show.showType,
  }).toString();
  let res = await fetch(
    process.env.NEXT_PUBLIC_HOST + "/api/search/" + show.id + "?" + searchParams
  );
  return await res.json();
}
