import React, {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import checkinService from "../../services/checkInService.ts";

const CancelCheckIn: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [checkInId, setCheckInId] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (location.state?.checkInId) {
            setCheckInId(location.state.checkInId);
        }
    }, [location.state]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!window.confirm('Are you sure you want to cancel this check-in? This action cannot be undone.')) {
            return;
        }

        setLoading(true);
        setError(null);

        try {
            await checkinService.cancelCheckIn(checkInId);
            alert('Check-in cancelled successfully!');
            navigate('/checkins');
        } catch (err: any) {
            setError(err.response?.data?.message || err.message || 'Failed to cancel check-in');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="checkin-form-container">
            <div className="form-header">
                <button onClick={() => navigate('/checkins')} className="back-button">
                    ← Back to Check-In Manager
                </button>
                <h1>Cancel Check-In</h1>
            </div>

            <form onSubmit={handleSubmit} className="checkin-form">
                {error && <div className="error-message">{error}</div>}

                <div className="form-group">
                    <label htmlFor="checkInId">Check-In ID *</label>
                    <input
                        type="text"
                        id="checkInId"
                        value={checkInId}
                        onChange={(e) => setCheckInId(e.target.value)}
                        required
                        placeholder="Enter check-in ID to cancel"
                    />
                </div>

                <div className="warning-message">
                    <strong>Warning:</strong> This action cannot be undone. The check-in will be permanently cancelled.
                </div>

                <div className="form-actions">
                    <button
                        type="submit"
                        disabled={loading || !checkInId.trim()}
                        className="submit-button"
                        style={{background: '#e74c3c'}}
                    >
                        {loading ? 'Cancelling...' : 'Cancel Check-In'}
                    </button>
                    <button type="button" onClick={() => navigate('/checkins')} className="cancel-button">
                        Go Back
                    </button>
                </div>
            </form>
        </div>
    );
};

export default CancelCheckIn;