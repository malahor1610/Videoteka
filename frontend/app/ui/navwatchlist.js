import {
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  NavLink,
  UncontrolledDropdown,
} from "reactstrap";

export default function NavWatchlist() {
  return (
    <UncontrolledDropdown nav inNavbar>
      <DropdownToggle nav caret>
        Lista
      </DropdownToggle>
      <DropdownMenu dark>
        <DropdownItem>
          <NavLink href="/watchlist">Wszystko</NavLink>
        </DropdownItem>
        <DropdownItem>
          <NavLink href="/watchlist/movies">Filmy</NavLink>
        </DropdownItem>
        <DropdownItem>
          <NavLink href="/watchlist/series">Seriale</NavLink>
        </DropdownItem>
      </DropdownMenu>
    </UncontrolledDropdown>
  );
}
