import { Toast, ToastBody, ToastHeader } from 'reactstrap';
import './App.css';

export default function Error({ error, setError }) {

  return (
    <Toast className="position-fixed bottom-0 end-0 p-2" isOpen={error}>
      <ToastHeader icon='danger' toggle={() => setError(!error)}>
        Error
      </ToastHeader>
      <ToastBody className='bg-danger '>
        {error}
      </ToastBody>
    </Toast>
  );
}
