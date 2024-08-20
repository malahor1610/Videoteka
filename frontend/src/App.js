import './App.css';
import Search from './search/Search';
import { Route, Routes } from 'react-router-dom';
import NavBar from './navigation/NavBar';
import Watchlist from './watchlist/Watchlist';
import WatchlistFiltered from './watchlist/WatchlistFiltered';
import Random from './random/Random';

export default function App() {
  return (
    <div className="App App-header">
      <Routes>
        <Route path='/' element={<Watchlist/>} />
        <Route path='/movies' element={<WatchlistFiltered type="MOVIE" />} />
        <Route path='/series' element={<WatchlistFiltered type="SERIES" />} />
        <Route path='/random' element={<Random />} />
        <Route path='/search' element={<Search />} />
      </Routes>
      <NavBar/>
    </div>
  );
}
