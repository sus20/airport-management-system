import {Routes, Route, Navigate} from 'react-router-dom';
import HomePage from './components/HomePage';
import FlightsManager from './components/FlightsManager';
import PassengersManager from './components/PassengersManager';
import './App.css';

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <div className="header-content">
                    <h1 onClick={() => window.location.href = '/'}>Airport Management System</h1>
                    <nav className="main-nav">
                        <a href="/">Home</a>
                        <a href="/flights">Flights</a>
                        <a href="/passengers">Passengers</a>
                    </nav>
                </div>
            </header>
            <main>
                <Routes>
                    <Route path="/" element={<HomePage/>}/>
                    <Route path="/flights" element={<FlightsManager/>}/>
                    <Route path="/flights/create" element={<FlightsManager/>}/>
                    <Route path="/flights/:flightId" element={<FlightsManager/>}/>
                    <Route path="/flights/:flightId/edit" element={<FlightsManager/>}/>

                    <Route path="/passengers" element={<PassengersManager/>}/>
                    <Route path="/passengers/create" element={<PassengersManager/>}/>
                    <Route path="/passengers/:passengerId" element={<PassengersManager/>}/>
                    <Route path="/passengers/:passengerId/edit" element={<PassengersManager/>}/>

                    <Route path="*" element={<Navigate to="/" replace/>}/>
                </Routes>
            </main>
        </div>
    );
}

export default App;