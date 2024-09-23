import { Button } from "reactstrap";

export default function TileWatchlistIcon({ icon, isButton, onClick }) {

  const bigScreenIcon = (
    <i
      className={
        icon + " bi d-flex d-sm-none justify-content-center fs-6 text-secondary"
      }
    ></i>
  );

  const smallScreenIcon = (
    <i
      className={
        icon + " bi d-none d-sm-flex justify-content-center fs-4 text-secondary"
      }
    ></i>
  );

  return isButton ? (
    <Button color="dark" className="p-1 ms-1 mb-1" onClick={onClick}>
      {bigScreenIcon}
      {smallScreenIcon}
    </Button>
  ) : (
    <>
      {bigScreenIcon}
      {smallScreenIcon}
    </>
  );
}
