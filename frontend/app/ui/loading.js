import { Spinner } from "reactstrap";

export default function Loading({ loading, details }) {
  return loading ? details ? LoadingDetails(loading = {loading}) : (
    <div className="fixed-top h-100 w-100">
      <Spinner className="position-fixed top-50"/>
    </div>
  ) : (
    <></>
  );
}

function LoadingDetails({ loading }) {
    return loading ? (
      <div className="fixed-bottom h-100 w-100">
        <Spinner className="position-fixed top-50" style={{left: "50%"}}/>
      </div>
    ) : (
      <></>
    );
  }
