import { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';

export default function App() {
  const [videos, setVideos] = useState([]);
  useEffect(() => {
    fetch("/videos")
      .then(result => result.json())
      .then(result => setVideos(result._embedded.videos));
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <div className="App-intro">
          <h2>In queue</h2>
          {videos.map(video =>
            <div key={video.id}>
              {video.title} ({video.type}) - {video.platform}
            </div>
          )}
        </div>
      </header>
    </div>
  );
}
