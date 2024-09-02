"use client";
import { Button, Collapse, Nav, Navbar, NavbarBrand, NavbarText, NavbarToggler, NavItem, NavLink } from "reactstrap";
import NavWatchlist from "./navwatchlist";
import { useState } from "react";

export function NavBar({ signOut, user }) {
  const [isOpen, setIsOpen] = useState(false);

  const toggle = () => setIsOpen(!isOpen);
  return (
    <Navbar color="dark" dark expand="sm" fixed="top">
      <NavbarBrand href="/">Videoteka</NavbarBrand>
      <NavbarToggler onClick={toggle} />
      <Collapse isOpen={isOpen} navbar>
        <Nav className="me-auto" navbar>
          <NavWatchlist />
          <NavItem>
            <NavLink href="/random">Losowe</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="/search">Szukaj</NavLink>
          </NavItem>
        </Nav>
        <NavbarText className="mx-3">
          {user?.signInDetails?.loginId}
        </NavbarText>
        <Button onClick={signOut}>Wyloguj</Button>
      </Collapse>
    </Navbar>
  );
}
