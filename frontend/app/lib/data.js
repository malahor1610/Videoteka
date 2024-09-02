import { InvokeCommand } from "@aws-sdk/client-lambda";
import { lambdaClient } from "./lambdaClient";
import { getCurrentUser } from "aws-amplify/auth";

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
      list,
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
  const { userId } = await getCurrentUser();
  payload.userId = userId;
  let res = await lambdaClient.send(
    new InvokeCommand({
      FunctionName: functionName,
      Payload: JSON.stringify(payload),
    })
  );
  return await JSON.parse(Buffer.from(res.Payload).toString());
}
