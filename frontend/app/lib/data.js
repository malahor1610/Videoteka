import { InvokeCommand } from "@aws-sdk/client-lambda";
import { lambdaClient } from "./lambdaClient";

export async function postShow(details, poster) {
  let res = await lambdaClient.send(new InvokeCommand({
    FunctionName: "addShowFunction",
    Payload: JSON.stringify({
      id: details.id,
      title: details.title,
      originalTitle: details.originalTitle,
      releaseDate: details.releaseDate,
      poster: poster,
      duration: details.duration,
      showType: details.showType
    })
  }));
  return await JSON.parse(Buffer.from(res.Payload).toString());
}

export async function updateShows(list) {
  let res = await lambdaClient.send(new InvokeCommand({
    FunctionName: "updateShowsFunction",
    Payload: JSON.stringify({
      list
    })
  }));
  return await JSON.parse(Buffer.from(res.Payload).toString());
}

export async function deleteShow(show) {
  let res = await lambdaClient.send(new InvokeCommand({
    FunctionName: "deleteShowFunction",
    Payload: JSON.stringify({
      id: show.id
    })
  }));
  return await JSON.parse(Buffer.from(res.Payload).toString());
}

export async function fetchAllShows() {
  let res = await lambdaClient.send(
    new InvokeCommand({
      FunctionName: "getShowsFunction",
      Payload: JSON.stringify({
        type: "ALL",
      }),
    })
  );
  return await JSON.parse(Buffer.from(res.Payload).toString());
}

export async function fetchShows(type) {
  let res = await lambdaClient.send(
    new InvokeCommand({
      FunctionName: "getShowsFunction",
      Payload: JSON.stringify({
        type: type,
      }),
    })
  );
  return await JSON.parse(Buffer.from(res.Payload).toString());
}

export async function fetchSearch(title, type) {
  let res = await lambdaClient.send(new InvokeCommand({
    FunctionName: "searchForShowsFunction",
    Payload: JSON.stringify({
      title: title,
      type: type
    })
  }));
  return await JSON.parse(Buffer.from(res.Payload).toString());
}

export async function fetchDetails(show) {
  let res = await lambdaClient.send(new InvokeCommand({
    FunctionName: "searchForDetailsFunction",
    Payload: JSON.stringify({
      id: show.id,
      type: show.showType
    })
  }));
  return await JSON.parse(Buffer.from(res.Payload).toString());
}
