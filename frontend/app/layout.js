"use client";
import { Authenticator } from "@aws-amplify/ui-react";
import "@aws-amplify/ui-react/styles.css";
import { Amplify } from "aws-amplify";
import "bootstrap-icons/font/bootstrap-icons.css";
import "bootstrap/dist/css/bootstrap.css";
import React, { useState } from "react";
import outputs from "../amplify_outputs.json";
import "./globals.css";
import { NavBar } from "./ui/navbar";
import Loading from "./ui/loading";

export const LoadingContext = React.createContext(null);

Amplify.configure(outputs);
export default function RootLayout({ children }) {
  const [loading, setLoading] = useState(false);

  return (
    <html lang="pl">
      <body
        className="align-center text-center text-white fs-3"
        style={{ backgroundColor: "rgb(40,44,52)" }}
      >
        <Authenticator hideSignUp variation="modal">
          {({ signOut, user }) => (
            <LoadingContext.Provider
              value={{ loading: loading, setLoading: setLoading }}
            >
              <NavBar className="row" user={user} signOut={signOut} />
              <main className="w-100 px-2 px-sm-5 mt-2 mt-sm-5 pt-5 justify-content-center">
                {children}
              </main>
            </LoadingContext.Provider>
          )}
        </Authenticator>
        <Loading loading={loading} />
      </body>
    </html>
  );
}
