"use client";
import { Button, Collapse, Nav, Navbar, NavbarBrand, NavbarText, NavbarToggler, NavItem, NavLink } from "reactstrap";
import NavWatchlist from "./navwatchlist";
import { useState } from "react";

export function NavBar() {
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
      </Collapse>
    </Navbar>
  );
}
