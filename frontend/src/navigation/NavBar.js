import { Nav, Navbar, NavbarText, NavItem, NavLink } from 'reactstrap';
import '../App.css';
import NavWatchlist from './NavWatchlist';

export default function NavBar() {
  return (
    <Navbar color='dark' dark expand fixed='top'>
      <Nav className="me-auto" navbar>
        <NavWatchlist />
        <NavItem>
          <NavLink href="/random">Random</NavLink>
        </NavItem>
        <NavItem>
          <NavLink href="/search">Search</NavLink>
        </NavItem>
      </Nav>
      <NavbarText>Videoteka</NavbarText>
    </Navbar>
  );
}
