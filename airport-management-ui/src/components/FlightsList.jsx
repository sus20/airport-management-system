// components/FlightsList.jsx
import React, { useState, useEffect } from 'react';
import flightService from '../services/flightService';
import './FlightsList.css';

const FlightsList = () => {
    const [flights, setFlights] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(5);

    useEffect(() => {
        loadFlights();
    }, [page, size]);

    const loadFlights = async () => {
        try {
            setLoading(true);
            console.log('Loading flights...');
            const data = await flightService.getFlights(page, size);
            console.log('Received data:', data);

            // Check if data is an array
            if (Array.isArray(data)) {
                setFlights(data);
                setError(null);
            } else {
                console.error('Expected array but got:', typeof data, data);
                setError('Invalid data format received from server');
            }
        } catch (err) {
            console.error('Error in loadFlights:', err);
            setError('Failed to load flights: ' + (err.message || 'Unknown error'));
        } finally {
            setLoading(false);
        }
    };

    const handlePrevious = () => {
        if (page > 0) {
            setPage(page - 1);
        }
    };

    const handleNext = () => {
        setPage(page + 1);
    };

    // Add more detailed loading and error states
    if (loading) return (
        <div className="flights-container">
            <h1>Flights</h1>
            <div className="loading">Loading flights...</div>
        </div>
    );

    if (error) return (
        <div className="flights-container">
            <h1>Flights</h1>
            <div className="error">Error: {error}</div>
            <button onClick={() => {
                setError(null);
                loadFlights();
            }}>Retry</button>
        </div>
    );

    return (
        <div className="flights-container">
            <h1>Flights</h1>

            <div className="pagination-controls">
                <button onClick={handlePrevious} disabled={page === 0}>
                    Previous
                </button>
                <span>Page {page + 1}</span>
                <button onClick={handleNext}>
                    Next
                </button>
                <select
                    value={size}
                    onChange={(e) => setSize(Number(e.target.value))}
                >
                    <option value="5">5 per page</option>
                    <option value="10">10 per page</option>
                    <option value="20">20 per page</option>
                </select>
            </div>

            <div className="flights-list">
                {flights.length === 0 ? (
                    <div className="no-flights">No flights found</div>
                ) : (
                    flights.map((flight) => (
                        <div key={flight.id} className="flight-card">
                            <h3>{flight.airline} {flight.flightNumber}</h3>
                            <p><strong>Route:</strong> {flight.origin} → {flight.destination}</p>
                            <p><strong>Departure:</strong> {new Date(flight.departureTime).toLocaleString()}</p>
                            <p><strong>Arrival:</strong> {new Date(flight.arrivalTime).toLocaleString()}</p>
                            <p><strong>Status:</strong>
                                <span className={`status ${flight.status?.toLowerCase()}`}>
                                    {flight.status}
                                </span>
                            </p>
                            <p><strong>Aircraft:</strong> {flight.aircraftType}</p>
                            <p><strong>Gate:</strong> {flight.gate} (Terminal {flight.terminal})</p>
                            <p><strong>Price:</strong> ${flight.price}</p>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default FlightsList;