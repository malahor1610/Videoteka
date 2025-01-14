"use client";
import { useState } from "react";
import { Button, UncontrolledTooltip } from "reactstrap";

export default function DescriptiveIcon({ id, onClick, color, icon, tooltip }) {
  const [targetId, setTargetId] = useState(id || "defaultTooltipTargetId");

  return (
    <>
      <div id={targetId}>
        <Button className="mx-1" color={color} onClick={onClick} disabled={!onClick}>
          <i className={"bi fs-5 " + icon}></i>
        </Button>
        <UncontrolledTooltip target={targetId}>{tooltip}</UncontrolledTooltip>
      </div>
    </>
  );
}
