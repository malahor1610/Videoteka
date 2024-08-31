import { Toast, ToastBody, ToastHeader } from "reactstrap";

export default function Notification({ message, type, setMessage }) {
  return (
    <Toast className="position-fixed bottom-0 end-0 p-2" isOpen={message}>
      <ToastHeader
        icon={type === "ok" ? "success" : "danger"}
        toggle={() => setMessage(hide())}
      >
        {type === "ok" ? "Ok" : "Błąd"}
      </ToastHeader>
      <ToastBody className={type === "ok" ? "bg-success" : "bg-danger"}>
        {message}
      </ToastBody>
    </Toast>
  );
}

export function error(message) {
  return { message: message, type: "error" };
}

export function success(message) {
  return { message: message, type: "ok" };
}

export function hide() {
  return { message: "", type: "" };
}
