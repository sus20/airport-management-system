import React, {useState} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import checkinService from "../../services/checkInService.ts";
import type {FlightCheckInResponse} from "../../types/checkIn.ts";
import "./CheckIn.css";
import type {ApiErrorResponse} from "../../services/checkInService.ts";
import CheckInDetails from "./CheckInDetails.tsx";
import UpdateCheckInSeats from "./UpdateCheckInSeats.tsx";
import CreateCheckIn from "./CreateCheckIn.tsx";
import CancelCheckIn from "./CancelCheckIn.tsx";
import CheckInsByFlight from "./CheckInsByFlight.tsx";

const CheckInsManager: React.FC = () => {
    const navigate = useNavigate();
    const {id} = useParams<{ id: string }>();
    const location = useLocation();

    const [checkInId, setCheckInId] = useState('');
    const [searchResult, setSearchResult] = useState<FlightCheckInResponse | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const isCreateView = location.pathname === '/checkins/create';
    const isDetailsView = id && location.pathname === `/checkins/${id}`;
    const isByFlightView = location.pathname === '/checkins/by-flight';
    const isUpdateSeatsView = location.pathname === '/checkins/update-seats';
    const isCancelView = location.pathname === '/checkins/cancel';

    const handleCreateCheckIn = () => {
        navigate('/checkins/create');
    };

    const handleViewCheckIn = async () => {
        if (!checkInId.trim()) return;

        setLoading(true);
        setError(null);
        setSearchResult(null);

        try {
            const checkInData = await checkinService.getCheckInById(checkInId.trim());
            navigate(`/checkins/${checkInData.id}`);
        } catch (err: any) {
            if (err.status && err.message) {
                const apiError = err as ApiErrorResponse;
                if (apiError.status === 404) {
                    setError(`Check-in with ID "${checkInId}" not found`);
                } else if (apiError.status === 400) {
                    setError(`Invalid check-in ID: ${apiError.message}`);
                } else {
                    setError(`${apiError.message} (Status: ${apiError.status})`);
                }
                if (apiError.errors && apiError.errors.length > 0) {
                    const validationErrors = apiError.errors.map(e => e.message).join(', ');
                    setError(`${apiError.message} - ${validationErrors}`);
                }
            } else {
                setError(err.message || 'Failed to fetch check-in details');
            }
            setSearchResult(null);
        } finally {
            setLoading(false);
        }
    };

    const handleKeyPress = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter' && checkInId.trim()) {
            handleViewCheckIn();
        }
    };

    const handleViewByFlight = () => {
        navigate('/checkins/by-flight');
    };

    const handleUpdateSeats = () => {
        navigate('/checkins/update-seats', {state: {checkInId: searchResult?.id || ''}});
    };

    const handleCancelCheckIn = () => {
        navigate('/checkins/cancel', {state: {checkInId: searchResult?.id || ''}});
    };

    if (isCreateView) {
        return <CreateCheckIn/>;
    }

    if (isDetailsView && id) {
        return <CheckInDetails/>;
    }

    if (isByFlightView) {
        return <CheckInsByFlight/>;
    }

    if (isUpdateSeatsView) {
        return <UpdateCheckInSeats/>;
    }

    if (isCancelView) {
        return <CancelCheckIn/>;
    }

    return (
        <div className="checkin-details-container">
            <div className="form-header">
                <button onClick={() => navigate('/')} className="back-button">
                    ← Back to Home
                </button>
                <h1>Check-In Management</h1>
            </div>

            <div className="operations-grid">

                <div className="operation-card">
                    <div className="operation-icon">➕</div>
                    <h3>Create New Check-In</h3>
                    <p>Create a new flight check-in for a passenger</p>
                    <div className="input-group">
                        <button
                            className="operation-button secondary"
                            onClick={handleCreateCheckIn}
                        >
                            Create Check-In
                        </button>
                    </div>
                </div>

                <div className="operation-card">
                    <div className="operation-icon">🎫</div>
                    <h3>View Check-In Details</h3>
                    <p>Search for check-in information by ID</p>
                    <div className="input-group">
                        <input
                            type="text"
                            placeholder="Enter Check-In ID"
                            value={checkInId}
                            onChange={(e) => setCheckInId(e.target.value)}
                            onKeyPress={handleKeyPress}
                            disabled={loading}
                        />
                        <button
                            className="operation-button secondary"
                            onClick={handleViewCheckIn}
                            disabled={!checkInId.trim() || loading}
                        >
                            {loading ? 'Searching...' : 'Search Check-In'}
                        </button>
                    </div>
                </div>

                <div className="operation-card">
                    <div className="operation-icon">✈️</div>
                    <h3>View Flight Check-Ins</h3>
                    <p>View all check-ins for a specific flight</p>
                    <div className="input-group">
                        <button
                            className="operation-button secondary"
                            onClick={handleViewByFlight}
                        >
                            View by Flight
                        </button>
                    </div>
                </div>

                <div className="operation-card">
                    <div className="operation-icon">💺</div>
                    <h3>Update Seat Assignment</h3>
                    <p>Modify seat numbers for an existing check-in</p>
                    <div className="input-group">
                        <button
                            className="operation-button warning"
                            onClick={handleUpdateSeats}
                        >
                            Update Seats
                        </button>
                    </div>
                </div>

                <div className="operation-card">
                    <div className="operation-icon">❌</div>
                    <h3>Cancel Check-In</h3>
                    <p>Cancel an existing flight check-in</p>
                    <div className="input-group">
                        <button
                            className="operation-button warning"
                            onClick={handleCancelCheckIn}
                            style={{background: '#e74c3c'}}
                        >
                            Cancel Check-In
                        </button>
                    </div>
                </div>
            </div>

            {error && (
                <div className="error-message" style={{marginTop: '20px'}}>
                    Error: {error}
                </div>
            )}

            {searchResult && (
                <div className="checkin-details" style={{marginTop: '30px'}}>
                    <h3>Check-In Details</h3>
                    <div className="detail-row">
                        <strong>Check-In ID:</strong>
                        <span>{searchResult.id}</span>
                    </div>
                    <div className="detail-row">
                        <strong>Flight Number:</strong>
                        <span>{searchResult.flightNumber}</span>
                    </div>
                    <div className="detail-row">
                        <strong>Seat Numbers:</strong>
                        <span>{searchResult.seatNumbers.join(', ')}</span>
                    </div>
                    <div className="detail-row">
                        <strong>Check-In Time:</strong>
                        <span>{new Date(searchResult.checkInTime).toLocaleString()}</span>
                    </div>
                    <div className="detail-row">
                        <strong>Status:</strong>
                        <span className={`status ${searchResult.status.toLowerCase()}`}>
                            {searchResult.status.replace('_', ' ')}
                        </span>
                    </div>
                    <div className="detail-row">
                        <strong>Baggage Count:</strong>
                        <span>{searchResult.baggages.length} items</span>
                    </div>

                    <div className="action-buttons" style={{marginTop: '20px'}}>
                        <button
                            onClick={() => navigate(`/checkins/${searchResult.id}`)}
                            className="action-button secondary"
                        >
                            View Full Details
                        </button>
                        <button
                            onClick={handleUpdateSeats}
                            className="action-button warning"
                        >
                            Update Seats
                        </button>
                        <button
                            onClick={handleCancelCheckIn}
                            className="action-button warning"
                            style={{background: '#e74c3c'}}
                        >
                            Cancel Check-In
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default CheckInsManager;