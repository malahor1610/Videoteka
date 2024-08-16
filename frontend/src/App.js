import './App.css';
import Search from './search/Search';
import { Route, Routes } from 'react-router-dom';
import NavBar from './navigation/NavBar';

export default function App() {
  return (
    <div className="App App-header">
      <Routes>
        <Route path='/search' element={<Search />} />
      </Routes>
      <NavBar/>
    </div>
  );
}
