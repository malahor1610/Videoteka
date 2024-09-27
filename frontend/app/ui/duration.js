import { useState } from "react";
import {
  Col,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownToggle,
  Row,
} from "reactstrap";

export default function Duration({ duration, seasons }) {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggle = () => setDropdownOpen((prevState) => !prevState);

  const generalDuration = (
    <small className="fs-6 fw-normal mb-0">{duration} </small>
  );

  return !seasons ? (
    <>
      <br />
      {generalDuration}
    </>
  ) : (
    <>
      <br />
      <Dropdown isOpen={dropdownOpen} toggle={toggle}>
        {generalDuration}
        <DropdownToggle data-toggle="dropdown" tag="span">
          <a role="button">
            <small className="btn-link fs-6 fw-normal mb-0 text-decoration-underline">
              (Ilość sezonów: {seasons.length})
            </small>
          </a>
        </DropdownToggle>
        <DropdownMenu>
          {seasons.map((season) => (
            <DropdownItem key={season} disabled>
              <Row>
                <Col>{season.name}</Col>
                <Col className="text-end">{season.episodes} odc.</Col>
              </Row>
            </DropdownItem>
          ))}
        </DropdownMenu>
      </Dropdown>
    </>
  );
}
