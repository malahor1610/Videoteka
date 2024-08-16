import { DropdownItem, DropdownMenu, DropdownToggle, NavLink, UncontrolledDropdown } from 'reactstrap';
import '../App.css';

export default function NavWatchlist() {
  return (
    <UncontrolledDropdown nav inNavbar>
      <DropdownToggle nav caret>
        Watchlist
      </DropdownToggle>
      <DropdownMenu right dark>
        <DropdownItem>
          <NavLink href="/all">All</NavLink>
        </DropdownItem>
        <DropdownItem>
          <NavLink href="/movies">Movies</NavLink>
        </DropdownItem>
        <DropdownItem>
          <NavLink href="/series">Series</NavLink>
        </DropdownItem>
      </DropdownMenu>
    </UncontrolledDropdown>
  );
}
