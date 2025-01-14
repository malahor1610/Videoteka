"use client";
import { Row, UncontrolledTooltip } from "reactstrap";
import ActionIcon from "./actionIcon";

export default function InfoIcon() {
  return (
    <div id="infoIcon" className="mx-2">
      <i className="bi bi-info-circle-fill text-info fs-5"></i>
      <UncontrolledTooltip target="infoIcon">
        <Row>
          <ActionIcon type="watch" description />
          <ActionIcon type="unwatch" description />
          <ActionIcon type="lock" description />
          <ActionIcon type="unlock" description />
          <ActionIcon type="collection" description />
          <ActionIcon type="remove" description />
          <ActionIcon type="disabledAdd" description />
          <ActionIcon type="add" description />
        </Row>
      </UncontrolledTooltip>
    </div>
  );
}
