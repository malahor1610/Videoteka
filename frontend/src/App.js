import logo from './logo.svg';
import './App.css';
import React, { Component } from 'react';

class App extends Component {
  state = {
    videos: []
  };

  async componentDidMount() {
    const response = await fetch('/videos');
    const body = await response.json();
    this.setState({videos: body._embedded.videos});
  }

  render() {
    const {videos} = this.state;
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
}
export default App;
