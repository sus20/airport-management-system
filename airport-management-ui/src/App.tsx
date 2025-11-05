import FlightsManager from './components/FlightsManager';
import './App.css';

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <h1>Airport Management System</h1>
            </header>
            <main>
                <FlightsManager/>
            </main>
        </div>
    );
}

export default App;