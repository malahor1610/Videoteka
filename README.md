# Videoteka

This is a learning project.

The application allows the users to create their own watchlists containing chosen movies and tv series. In addition users can display some more detailed information about the shows and pick a random one to watch.

The shows are provided by [TMDB API](https://developer.themoviedb.org/docs/getting-started).

## Stack

- Java 21
- Quarkus
- Next.js
- AWS DynamoDB
- AWS Cognito

## Features

### 1.0

- Signing in using AWS Cognito user pool,
- Searching for movies or series by title,
- Displaying details about a show - original & polish titles, release date, duration, overview, genres, availability on streaming platforms,
- Adding a show to the watchlist,
- Displaying and managing the watchlist - remove a show, reorder,
- Displaying a watchlist in scope of movies only or series only,
- Picking a random movie or series of the watchlist with possibility to exclude the shows from the randomize pool.

### 1.1

- Displaying more details about a show - accurate date for upcoming releases, info about new tv series season,
- Displaying a collection of movies (list of movies belonging to the same series),
- Moving shows on the watchlist to the first/last position,
- Turning on the notifications about tv series, to display information when the series has an "in production" status, or the release date is confirmed, also about new seasons,
- Adding the excluded shows back to randomize pool.

### 1.2

- Clearing input button for searching functionality,
- Displaying more details about a show - list tv series seasons with names and episodes count,
- Filtering shows on the watchlist based on the genres,
- Filtering shows in the randomize pool based on the genres,
- Marking shows as watched/unwatched,
- Displaying a list of shows marked as watched.

### 1.3.0

- Searching for the shows by title (PL and Original) on the watchlist,
- Displaying information about new upcoming episode of ongoing series,
- Displaying notification for the movies about the release date,
- Turning on notifications by default for the series marked as watched (so they can be removed from watchlist and still be applicable for notifications),
- Filtering out the shows in the randomize pool which are not yet released,
- Filtering out the shows in the randomize pool which notifications are turned on for, so that the "waiting for continuation series" are not included within the pool,
- Supporting browser's back button to not lose the context:
     - within searching (title, show type, results),
     - within random (show type),
     - within watched (show type),

## Local run

### Prerequisites

- AWS account with configured AWS Cognito user pool
- Local DynamoDB / docker container / configured DynamoDB on AWS account
- TMDB API key
- NodeJS

### Steps

1. Configure DynamoDB based on [AWS guide](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.DownloadingAndRunning.html) and set up properties correctly (according to Your configuration) in ```application.properties``` file
2. Configure Cognito:
    - [Create user pool](https://docs.aws.amazon.com/cognito/latest/developerguide/user-pool-next-steps.html#tutorial-create-user-pool),
    - [Define some users](https://docs.aws.amazon.com/cognito/latest/developerguide/managing-users.html),
    - [Define client application](https://docs.aws.amazon.com/cognito/latest/developerguide/user-pool-settings-client-apps.html#cognito-user-pools-app-idp-settings-console-create),
    - [Provide hosted UI for client app](https://docs.aws.amazon.com/cognito/latest/developerguide/cognito-user-pools-app-integration.html#cognito-user-pools-create-an-app-integration) with localhost nextjs callback & sign-out urls by default http://localhost:3000/callback & http://localhost:3000
    - Copy the Token signing key URL attribute of user pool to ```quarkus.oidc.auth-server-url``` property in ```application.properties``` file
    - Copy client application id & cognito domain of user pool to ```.env``` file in frontend dir (create it if missing). The result should be as follows:
      ```
      NEXT_PUBLIC_HOST=http://localhost:8080 (URL OF QUARKUS SERVICE BY DEFAULT)
      NEXT_PUBLIC_CLIENT_ID=your-app-client-id
      NEXT_PUBLIC_HOSTED_UI_DOMAIN=your-cognito-user-pool-domain
      NEXT_PUBLIC_CALLBACK=http://localhost:3000/callback
      NEXT_PUBLIC_SIGN_OUT=http://localhost:3000
      ```
3. Set TMDB api key to ```api.key``` property in ```application.properties``` file
4. Start quarkus application using command in project dir
``` shell
.\mvnw.cmd quarkus:dev -Pdev
```
5. Start frontend using command in projectDir/frontend
```shell
npm run dev
```