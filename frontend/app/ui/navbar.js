'use client'
import { Button, Nav, Navbar, NavbarText, NavItem, NavLink } from "reactstrap";
import NavWatchlist from "./navwatchlist";

export function NavBar({signOut, user}) {
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
        <NavbarText className="mx-3">Witaj, {user?.username}</NavbarText>
        <Button onClick={signOut}>Wyloguj</Button>
      </Navbar>
    );
  }
  