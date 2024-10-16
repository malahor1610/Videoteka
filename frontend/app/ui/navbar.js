"use client";
import {
  Button,
  Collapse,
  Nav,
  Navbar,
  NavbarBrand,
  NavbarText,
  NavbarToggler,
  NavItem,
  NavLink,
} from "reactstrap";
import NavWatchlist from "./navwatchlist";
import { useEffect, useState } from "react";
import { logout, parseJwt } from "../lib/data";

export function NavBar({ user }) {
  const [isOpen, setIsOpen] = useState(false);
  const [username, setUsername] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem("idToken");
    if (token) {
      const decodedToken = parseJwt(token);
      if (decodedToken) {
        setUsername(decodedToken["cognito:username"]);
      }
    }
  }, []);

  const toggle = () => setIsOpen(!isOpen);
  function signOut() {
    logout();
  }
  return (
    <Navbar color="dark" dark expand="sm" fixed="top">
      <NavbarBrand href="/watchlist">Videoteka</NavbarBrand>
      <NavbarToggler onClick={toggle} />
      <Collapse isOpen={isOpen} navbar>
        <Nav className="me-auto" navbar>
          <NavWatchlist />
          <NavItem>
            <NavLink href="/watched">Obejrzane</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="/random">Losowe</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="/search">Szukaj</NavLink>
          </NavItem>
        </Nav>
        <NavbarText className="mx-3">{username}</NavbarText>
        <Button onClick={signOut}>Wyloguj</Button>
      </Collapse>
    </Navbar>
  );
}
