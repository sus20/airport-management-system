import React, {useState, useEffect} from "react";
import flightService from "../../services/flightService.ts";
import {type Flight, FlightStatus} from '../../types/flight.ts';
import './FlightDetails.css'


interface FlightDetailsProps {
    flightId: string;
    onBack: () => void;
    onFlightUpdate: (flight: Flight) => void;
}

const FlightDetails: React.FC<FlightDetailsProps> = ({flightId, onBack, onFlightUpdate}) => {
    const [flight, setFlight] = useState<Flight | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [updating, setUpdating] = useState(false);

    useEffect(() => {
        loadFlight();
    }, [flightId]);

    const loadFlight = async () => {
        try {
            setLoading(true);
            const flightData = await flightService.getFlightById(flightId);
            setFlight(flightData);
        } catch (err) {
            setError('Failed to load flight details');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleStatusUpdate = async (newStatus: FlightStatus) => {
        try {
            setUpdating(true);
            const updatedFlight = await flightService.updateStatus(flightId, {status: newStatus});
            setFlight(updatedFlight);
            onFlightUpdate(updatedFlight);
        } catch (err) {
            setError('Failed to update status');
            console.error(err);
        } finally {
            setUpdating(false);
        }
    };

    const handleGateUpdate = async (newGate: string) => {
        if (!newGate.trim()) return;

        try {
            setUpdating(true);
            const updatedFlight = await flightService.updateGate(flightId, {gate: newGate});
            setFlight(updatedFlight);
            onFlightUpdate(updatedFlight)
        } catch (err) {
            setError('Failed to update gate');
            console.error(err);
        } finally {
            setUpdating(false);
        }
    };

    const handleTerminalUpdate = async (newTerminal: string) => {
        if (!newTerminal.trim()) return;

        try {
            setUpdating(true);
            const updatedFlight = await flightService.updateTerminal(flightId, {terminal: newTerminal});
            setFlight(updatedFlight);
            onFlightUpdate(updatedFlight);
        } catch (err) {
            setError('Failed to update terminal');
            console.error(err);
        } finally {
            setUpdating(false);
        }
    };

    if (loading) return <div className="loading"> Loading flight details ... </div>;
    if (error) return <div className="error"> Error: {error}</div>;
    if (!flight) return <div className="error">Flight not found</div>;

    return (
        <div className="flight-details">
            <button onClick={onBack} className="back-button">← Back to List</button>

            <div className="flight-header">
                <h2>{flight.airline} {flight.flightNumber}</h2>
                <span className={`status ${flight.status.toLowerCase()}`}>
                     {flight.status}
                </span>
            </div>

            <div className="flight-info">
                <div className="info-section">
                    <h3> Route Information</h3>
                    <p><strong>From:</strong> {flight.origin}</p>
                    <p><strong>To:</strong> {flight.destination}</p>
                    <p><strong>Departure:</strong> {new Date(flight.departureTime).toLocaleString()}</p>
                    <p><strong>Arrival:</strong> {new Date(flight.arrivalTime).toLocaleString()}</p>
                </div>

                <div className="info-section">
                    <h3>Aircraft & Facilities</h3>
                    <p><strong>Aircraft:</strong> {flight.aircraftType}</p>
                    <p>
                        <strong>Gate:</strong>
                        <input
                            type="text"
                            defaultValue={flight.gate}
                            onBlur={(e) => handleGateUpdate(e.target.value)}
                            disabled={updating}
                        />
                    </p>
                    <p>
                        <strong>Terminal:</strong>
                        <input
                            type="text"
                            defaultValue={flight.terminal}
                            onBlur={(e) => handleTerminalUpdate(e.target.value)}
                            disabled={updating}
                        />
                    </p>
                </div>

                <div className="info-section">
                    <h3>Pricing</h3>
                    <p><strong>Price:</strong> ${flight.price}</p>
                </div>

                <div className="info-section">
                    <h3>Update Status</h3>
                    <div className="status-buttons">
                        {Object.values(FlightStatus).map(status => (
                            <button
                                key={status}
                                onClick={() => handleStatusUpdate(status)}
                                disabled={updating || flight.status === status}
                                className={flight.status === status ? 'active' : ''}
                            >
                                {status}
                            </button>
                        ))}
                    </div>
                </div>
            </div>
        </div>

    );

};

export default FlightDetails;