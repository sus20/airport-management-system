import React from 'react';
import FlightsList from './components/FlightsList';
import './App.css';

function App() {
    return (
        <div className="App">
            <h1> Airport Management System</h1>
            <FlightsList />
        </div>
    );
}

export default App;