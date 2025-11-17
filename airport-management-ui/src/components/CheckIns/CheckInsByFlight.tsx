import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import checkinService from '../../services/checkInService';
import type {FlightCheckInResponse} from '../../types/checkIn';
import './CheckIn.css';

const CheckInsByFlight: React.FC = () => {
    const navigate = useNavigate();
    const [hasSearched, setHasSearched] = useState(false);
    const [flightNumber, setFlightNumber] = useState('');
    const [checkIns, setCheckIns] = useState<FlightCheckInResponse[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSearch = async () => {
        if (!flightNumber.trim()) return;

        setHasSearched(true);
        setLoading(true);
        setError(null);
        setCheckIns([]);

        try {
            const checkInsData = await checkinService.getCheckInsByFlight(flightNumber.trim());
            setCheckIns(checkInsData);
        } catch (err: any) {
            setError(err.response?.data?.message || err.message || 'Failed to fetch check-ins');
        } finally {
            setLoading(false);
        }
    };

    const handleKeyPress = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter' && flightNumber.trim()) {
            handleSearch();
        }
    };

    return (
        <div className="checkin-details-container">
            <div className="form-header">
                <button onClick={() => navigate('/checkins')} className="back-button">
                    ← Back to Check-In Manager
                </button>
                <h1>Check-Ins by Flight</h1>
            </div>

            <div className="search-section">
                <div className="form-group">
                    <label htmlFor="flightNumber">Flight Number</label>
                    <input
                        type="text"
                        id="flightNumber"
                        value={flightNumber}
                        onChange={(e) => setFlightNumber(e.target.value)}
                        onKeyPress={handleKeyPress}
                        placeholder="Enter flight number (e.g., EK101)"
                        disabled={loading}
                    />
                    <button
                        onClick={handleSearch}
                        disabled={!flightNumber.trim() || loading}
                        className="operation-button secondary"
                        style={{marginTop: '10px'}}
                    >
                        {loading ? 'Searching...' : 'Search'}
                    </button>
                </div>
            </div>

            {error && (
                <div className="error-message" style={{marginTop: '20px'}}>
                    Error: {error}
                </div>
            )}

            {checkIns.length > 0 && (
                <div className="checkins-list" style={{marginTop: '30px'}}>
                    <h3>Check-Ins for Flight {flightNumber}</h3>
                    {checkIns.map((checkIn) => (
                        <div key={checkIn.id} className="checkin-item">
                            <div className="detail-row">
                                <strong>Check-In ID:</strong>
                                <span>{checkIn.id}</span>
                            </div>
                            <div className="detail-row">
                                <strong>Seats:</strong>
                                <span>{checkIn.seatNumbers.join(', ')}</span>
                            </div>
                            <div className="detail-row">
                                <strong>Status:</strong>
                                <span className={`status ${checkIn.status.toLowerCase()}`}>
                                    {checkIn.status.replace('_', ' ')}
                                </span>
                            </div>
                            <div className="detail-row">
                                <strong>Check-In Time:</strong>
                                <span>{new Date(checkIn.checkInTime).toLocaleString()}</span>
                            </div>
                            <div className="action-buttons">
                                <button
                                    onClick={() => navigate(`/checkins/${checkIn.id}`)}
                                    className="action-button secondary"
                                >
                                    View Details
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            )}

            {hasSearched && checkIns.length === 0 && !loading && flightNumber && !error && (
                <div className="no-results" style={{marginTop: '20px', textAlign: 'center'}}>
                    No check-ins found for flight {flightNumber}
                </div>
            )}
        </div>
    );
};

export default CheckInsByFlight;