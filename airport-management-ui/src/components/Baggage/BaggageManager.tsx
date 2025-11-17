import React, {useState, useEffect} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import baggageService from "../../services/baggageService.ts";
import type {BaggageResponse} from "../../types/baggage.ts";
import "./Baggage.css";
import type {ApiErrorResponse} from "../../services/flightService.ts";
import BaggageDetails from "./BaggageDetails.tsx";
import UpdateBaggageWeight from "./UpdateBaggageWeight.tsx";
import UpdateBaggageStatus from "./UpdateBaggageStatus.tsx";

const BaggageManager: React.FC = () => {
    const navigate = useNavigate();
    const {id} = useParams<{ id: string }>();
    const location = useLocation();

    const [baggageId, setBaggageId] = useState('');
    const [searchResult, setSearchResult] = useState<BaggageResponse | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const isDetailsView = id && location.pathname === `/baggage/${id}`;
    const isUpdateWeightView = location.pathname === '/baggage/update-weight';
    const isUpdateStatusView = location.pathname === '/baggage/update-status';

    useEffect(() => {
        if (location.pathname === '/baggage') {
            setSearchResult(null);
            setBaggageId('');
            setError(null);
        }
    }, [location.pathname]);

    const handleViewBaggage = async () => {
        if (!baggageId.trim()) return;

        setLoading(true);
        setError(null);
        setSearchResult(null);

        try {
            console.log("I am in try catch block inside handleViewBaggage!")
            const baggageData = await baggageService.getBaggageById(baggageId.trim());
            setSearchResult(baggageData);
            navigate(`/baggage/${baggageId.trim()}`);
        } catch (err: any) {
            if (err.status && err.message) {
                const apiError = err as ApiErrorResponse;
                if (apiError.status === 404) {
                    setError(`Baggage with ID "${baggageId}" not found`);
                } else if (apiError.status === 400) {
                    setError(`Invalid baggage ID: ${apiError.message}`);
                } else {
                    setError(`${apiError.message} (Status: ${apiError.status})`);
                }
                if (apiError.errors && apiError.errors.length > 0) {
                    const validationErrors = apiError.errors.map(e => e.message).join(', ');
                    setError(`${apiError.message} - ${validationErrors}`);
                }
            } else {
                setError(err.message || 'Failed to fetch baggage details');
            }
            setSearchResult(null);
        } finally {
            setLoading(false);
        }
    };

    const handleKeyPress = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter' && baggageId.trim()) {
            handleViewBaggage();
        }
    };

    const handleUpdateWeight = () => {
        navigate('/baggage/update-weight', {state: {baggageId: searchResult?.id || ''}});
    };

    const handleUpdateStatus = () => {
        navigate('/baggage/update-status', {state: {baggageId: searchResult?.id || ''}});
    };

    if (isDetailsView && id) {
        return <BaggageDetails/>;
    }

    if (isUpdateWeightView) {
        return <UpdateBaggageWeight/>;
    }

    if (isUpdateStatusView) {
        return <UpdateBaggageStatus/>;
    }

    return (
        <div className="baggage-details-container">
            <div className="form-header">
                <button onClick={() => navigate('/')} className="back-button">
                    ← Back to Home
                </button>
                <h1>Baggage Management</h1>
            </div>

            <div className="operations-grid">
                <div className="operation-card">
                    <div className="operation-icon">🧳</div>
                    <h3>View Baggage Details</h3>
                    <p>Search for baggage information by ID</p>
                    <div className="input-group">
                        <input
                            type="text"
                            placeholder="Enter Baggage ID"
                            value={baggageId}
                            onChange={(e) => setBaggageId(e.target.value)}
                            onKeyPress={handleKeyPress}
                            disabled={loading}
                        />
                        <button
                            className="operation-button secondary"
                            onClick={handleViewBaggage}
                            disabled={!baggageId.trim() || loading}
                        >
                            {loading ? 'Searching...' : 'Search Baggage'}
                        </button>
                    </div>
                </div>

                <div className="operation-card">
                    <div className="operation-icon">⚖️</div>
                    <h3>Update Baggage Weight</h3>
                    <p>Modify baggage weight information</p>
                    <div className="input-group">
                        <button
                            className="operation-button warning"
                            onClick={handleUpdateWeight}
                        >
                            Update Weight
                        </button>
                    </div>
                </div>

                <div className="operation-card">
                    <div className="operation-icon">🔄</div>
                    <h3>Update Baggage Status</h3>
                    <p>Change baggage status (Checked-in, Loaded, etc.)</p>
                    <div className="input-group">
                        <button
                            className="operation-button warning"
                            onClick={handleUpdateStatus}
                        >
                            Update Status
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
                <div className="baggage-details" style={{marginTop: '30px'}}>
                    <h3>Baggage Details</h3>
                    <div className="detail-row">
                        <strong>Baggage ID:</strong>
                        <span>{searchResult.id}</span>
                    </div>
                    <div className="detail-row">
                        <strong>Tag Number:</strong>
                        <span>{searchResult.tagNumber}</span>
                    </div>
                    <div className="detail-row">
                        <strong>Weight:</strong>
                        <span>{searchResult.weight} kg</span>
                    </div>
                    <div className="detail-row">
                        <strong>Status:</strong>
                        <span className={`status ${searchResult.status.toLowerCase()}`}>
                            {searchResult.status.replace('_', ' ')}
                        </span>
                    </div>

                    <div className="action-buttons" style={{marginTop: '20px'}}>
                        <button
                            onClick={() => navigate(`/baggage/${searchResult.id}`)}
                            className="action-button secondary"
                        >
                            View Full Details
                        </button>
                        <button
                            onClick={handleUpdateWeight}
                            className="action-button warning"
                        >
                            Update Weight
                        </button>
                        <button
                            onClick={handleUpdateStatus}
                            className="action-button warning"
                        >
                            Update Status
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
};

export default BaggageManager;