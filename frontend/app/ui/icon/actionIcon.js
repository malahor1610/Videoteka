"use client";
import DescriptiveIcon from "./descriptiveIcon";
import IconDescription from "./iconDescription";

export default function ActionIcon({
  onClick,
  tooltipData,
  type,
  description,
}) {
  const watchMetadata = {
    id: "watchDI",
    color: "warning",
    icon: "bi-eye",
    tooltip: "Oznacz jako obejrzane",
  };
  const unwatchMetadata = {
    id: "unwatchDI",
    color: "secondary",
    icon: "bi-eye-slash",
    tooltip: "Oznacz jako nieobejrzane",
  };
  const collectionMetadata = {
    id: "collectionDI",
    color: "success",
    icon: "bi-list-ol",
    tooltip: "Przejdź do kolekcji " + (tooltipData || ""),
  };
  const addMetadata = {
    id: "addDI",
    color: "primary",
    icon: "bi-plus-circle",
    tooltip: "Dodaj do listy",
  };
  const disabledAddMetadata = {
    id: "disabledAddDI",
    color: "primary",
    icon: "bi-plus-circle",
    tooltip: "Pozycja jest już na liście",
    disabled: true,
  };
  const lockMetadata = {
    id: "lockDI",
    color: "info",
    icon: "bi-bell",
    tooltip: "Włącz powiadomienia",
  };
  const unlockMetadata = {
    id: "unlockDI",
    color: "secondary",
    icon: "bi-bell-slash",
    tooltip: "Wyłącz powiadomienia",
  };
  const removeMetadata = {
    id: "removeDI",
    color: "danger",
    icon: "bi-x-circle",
    tooltip: "Usuń z listy",
  };
  let metadata;
  switch (type) {
    case "watch":
      metadata = watchMetadata;
      break;
    case "unwatch":
      metadata = unwatchMetadata;
      break;
    case "collection":
      metadata = collectionMetadata;
      break;
    case "add":
      metadata = addMetadata;
      break;
    case "disabledAdd":
      metadata = disabledAddMetadata;
      break;
    case "lock":
      metadata = lockMetadata;
      break;
    case "unlock":
      metadata = unlockMetadata;
      break;
    case "remove":
      metadata = removeMetadata;
      break;
    default:
      metadata = watchMetadata;
      break;
  }

  return description ? (
    <IconDescription
      color={metadata.color}
      icon={metadata.icon}
      tooltip={metadata.tooltip}
      disabled={metadata.disabled}
    />
  ) : (
    <DescriptiveIcon
      id={metadata.id}
      onClick={onClick}
      color={metadata.color}
      icon={metadata.icon}
      tooltip={metadata.tooltip}
    />
  );
}
