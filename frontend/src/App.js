import './App.css';
import Search from './search/Search';
import { Route, Routes } from 'react-router-dom';
import NavBar from './navigation/NavBar';
import Watchlist from './watchlist/Watchlist';

export default function App() {
  return (
    <div className="App App-header">
      <Routes>
        <Route path='/' element={<Watchlist type="ALL" />} />
        <Route path='/search' element={<Search />} />
        <Route path='/movies' element={<Watchlist type="MOVIE" />} />
        <Route path='/series' element={<Watchlist type="SERIES" />} />
      </Routes>
      <NavBar/>
    </div>
  );
}
