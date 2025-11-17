import React, {useState, useEffect} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
import checkinService, {type ApiErrorResponse} from '../../services/checkInService';
import type {FlightCheckInResponse} from '../../types/checkIn';
import './CheckIn.css';

const CheckInDetails: React.FC = () => {
    const {id} = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [checkIn, setCheckIn] = useState<FlightCheckInResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchCheckIn = async () => {
            if (!id) return;

            try {
                setLoading(true);
                setError(null);
                const checkInData = await checkinService.getCheckInById(id);
                setCheckIn(checkInData);
            } catch (err: any) {
                if (err.status && err.message) {
                    const apiError = err as ApiErrorResponse;
                    setError(`${apiError.message} (Status: ${apiError.status})`);

                    if (apiError.errors && apiError.errors.length > 0) {
                        const validationErrors = apiError.errors.map(e => `${e.field}: ${e.message}`).join(', ');
                        setError(`${apiError.message} - ${validationErrors}`);
                    }
                } else {
                    setError(err.message || 'Failed to fetch check-in details');
                }
            } finally {
                setLoading(false);
            }
        };
        fetchCheckIn();
    }, [id]);

    if (loading) return <div className="loading">Loading check-in details...</div>;
    if (error) return <div className="error">Error: {error}</div>;
    if (!checkIn) return <div className="error">Check-in not found</div>;

    return (
        <div className="checkin-details-container">
            <div className="form-header">
                <button onClick={() => navigate('/checkins')} className="back-button">
                    ← Back to Check-In Manager
                </button>
                <h1>Check-In Details</h1>
            </div>

            <div className="checkin-details">
                <div className="detail-row">
                    <strong>Check-In ID:</strong>
                    <span>{checkIn.id}</span>
                </div>
                <div className="detail-row">
                    <strong>Flight Number:</strong>
                    <span>{checkIn.flightNumber}</span>
                </div>
                <div className="detail-row">
                    <strong>Seat Numbers:</strong>
                    <span>{checkIn.seatNumbers.join(', ')}</span>
                </div>
                <div className="detail-row">
                    <strong>Check-In Time:</strong>
                    <span>{new Date(checkIn.checkInTime).toLocaleString()}</span>
                </div>
                <div className="detail-row">
                    <strong>Status:</strong>
                    <span className={`status ${checkIn.status.toLowerCase()}`}>
                        {checkIn.status.replace('_', ' ')}
                    </span>
                </div>
                <div className="detail-row">
                    <strong>Boarding Pass:</strong>
                    <span>
                        {checkIn.boardingPassUrl ? (
                            <a href={checkIn.boardingPassUrl} target="_blank" rel="noopener noreferrer">
                                View Boarding Pass
                            </a>
                        ) : (
                            'Not available'
                        )}
                    </span>
                </div>

                <div className="baggage-section">
                    <h3>Baggage Information</h3>
                    {checkIn.baggages.length === 0 ? (
                        <p>No baggage registered</p>
                    ) : (
                        checkIn.baggages.map((baggage, index) => (
                            <div key={baggage.id} className="baggage-item">
                                <div className="detail-row">
                                    <strong>Baggage {index + 1}:</strong>
                                    <span>{baggage.tagNumber}</span>
                                </div>
                                <div className="detail-row">
                                    <strong>Weight:</strong>
                                    <span>{baggage.weight} kg</span>
                                </div>
                                <div className="detail-row">
                                    <strong>Status:</strong>
                                    <span className={`status ${baggage.status.toLowerCase()}`}>
                                        {baggage.status.replace('_', ' ')}
                                    </span>
                                </div>
                            </div>
                        ))
                    )}
                </div>
            </div>

            <div className="action-buttons">
                <button
                    onClick={() => navigate('/checkins/update-seats', {state: {checkInId: checkIn.id}})}
                    className="action-button warning"
                >
                    Update Seats
                </button>
                <button
                    onClick={() => navigate('/checkins/cancel', {state: {checkInId: checkIn.id}})}
                    className="action-button warning"
                    style={{background: '#e74c3c'}}
                >
                    Cancel Check-In
                </button>
            </div>
        </div>
    );
};

export default CheckInDetails;