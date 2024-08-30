'use client'
import { Nav, Navbar, NavbarText, NavItem, NavLink } from "reactstrap";
import NavWatchlist from "./navwatchlist";

export function NavBar() {
    return (
      <Navbar color='dark' dark expand fixed='top'>
        <Nav className="me-auto" navbar>
          <NavWatchlist />
          <NavItem>
            <NavLink href="/random">Losowe</NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="/search">Szukaj</NavLink>
          </NavItem>
        </Nav>
        <NavbarText>Videoteka</NavbarText>
      </Navbar>
    );
  }
  