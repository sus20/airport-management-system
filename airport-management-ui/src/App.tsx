import {Routes, Route, Navigate} from 'react-router-dom';
import HomePage from './components/HomePage/HomePage.tsx';
import FlightsManager from './components/Flights/FlightsManager.tsx';
import PassengersManager from './components/Passengers/PassengersManager.tsx';
import BaggageManager from "./components/Baggage/BaggageManager.tsx";
import CheckInsManager from "./components/CheckIns/CheckInsManager.tsx";
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
                        <a href="/checkins">Check-Ins</a>
                        <a href="/baggage">Baggage</a>
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

                    <Route path="/checkins" element={<CheckInsManager/>}/>
                    <Route path="/checkins" element={<CheckInsManager/>}/>
                    <Route path="/checkins/:id" element={<CheckInsManager/>}/>
                    <Route path="/checkins/create" element={<CheckInsManager/>}/>
                    <Route path="/checkins/update-seats" element={<CheckInsManager/>}/>
                    <Route path="/checkins/cancel" element={<CheckInsManager/>}/>
                    <Route path="/checkins/by-flight" element={<CheckInsManager/>}/>

                    <Route path="/baggage" element={<BaggageManager/>}/>
                    <Route path="/baggage/:id" element={<BaggageManager/>}/>
                    <Route path="baggage/update-weight" element={<BaggageManager/>}/>
                    <Route path="/baggage/update-status" element={<BaggageManager/>}/>


                    <Route path="*" element={<Navigate to="/" replace/>}/>
                </Routes>
            </main>
        </div>
    );
}

export default App;