import { Row } from "reactstrap";

export default function WatchProviders({ type, providers }) {
  return (
    <div>
      {type}
      <Row>
        {providers?.map((provider) => (
          <img
            key={provider}
            className="col-lg-1 col-2 my-1 "
            src={provider}
            alt="No logo available"
          />
        ))}
      </Row>
    </div>
  );
}
