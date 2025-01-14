"use client";

import { Button, Col, Row } from "reactstrap";

export default function IconDescription({ color, icon, tooltip, disabled }) {
  return (
    <>
      <Col xs="6" className="my-1">
        <Row className="justify-content-center">
          <Button
            className="w-auto py-0 px-1"
            color={color}
            disabled={disabled}
          >
            <i className={"bi fs-6 " + icon}></i>
          </Button>
        </Row>
        <Row>
          <small className="fw-light lh-sm">{tooltip}</small>
        </Row>
      </Col>
    </>
  );
}
