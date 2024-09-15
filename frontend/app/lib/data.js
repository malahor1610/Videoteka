export async function postShow(details, poster) {
  return await execute(
    "addShowFunction",
    {
      id: details.id,
      title: details.title,
      originalTitle: details.originalTitle,
      releaseDate: details.releaseDate,
      poster: poster,
      duration: details.duration,
      showType: details.showType,
    }
  );
}

export async function updateShows(list) {
  return await execute(
    "updateShowsFunction",
    {
      shows: list,
    }
  );
}

export async function deleteShow(show) {
  return await execute(
    "deleteShowFunction",
    {
      id: show.id,
    }
  );
}

export async function fetchAllShows() {
  return await execute(
    "getShowsFunction",
    {
      type: "ALL",
    }
  );
}

export async function fetchShows(type) {
  return await execute(
    "getShowsFunction",
    {
      type: type,
    }
  );
}

export async function fetchSearch(title, type) {
  return await execute(
    "searchForShowsFunction",
    {
      title: title,
      type: type,
    }
  );
}

export async function fetchDetails(show) {
  return await execute(
    "searchForDetailsFunction",
    {
      id: show.id,
      type: show.showType,
    }
  );
}

async function execute(functionName, payload) {
  const { userId } = 'technical';
  payload.userId = userId;
  let res = await fetch('http://localhost:8080/' + functionName, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload)
  });
  return await res.json();
}
