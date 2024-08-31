import "./globals.css";
import { NavBar } from "./ui/navbar";
import 'bootstrap/dist/css/bootstrap.css'
import "bootstrap-icons/font/bootstrap-icons.css";

export const metadata = {
  title: "Videoteka",
};

export default function RootLayout({ children }) {
  return (
    <html lang="pl">
      <body className="align-center text-center text-white fs-3" style={{'background-color': 'rgb(40,44,52)'}}>
        <NavBar className="row"/>
        <main className="w-100 px-2 px-sm-5 mt-2 mt-sm-5 pt-5 justify-content-center">{children}</main>
      </body>
    </html>
  );
}
