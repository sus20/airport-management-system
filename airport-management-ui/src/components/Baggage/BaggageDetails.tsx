import React, {useState, useEffect} from 'react';
import {useParams, useNavigate} from 'react-router-dom';
import baggageService, {type ApiErrorResponse} from '../../services/baggageService';
import type {BaggageResponse} from '../../types/baggage';
import './Baggage.css';

const BaggageDetails: React.FC = () => {
    const {id} = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [baggage, setBaggage] = useState<BaggageResponse | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchBaggage = async () => {
            if (!id) return;

            try {
                setLoading(true);
                setError(null);
                const baggageData = await baggageService.getBaggageById(id);
                setBaggage(baggageData);
            } catch (err: any) {

                if (err.status && err.message) {
                    const apiError = err as ApiErrorResponse;
                    setError(`${apiError.message} (Status: ${apiError.status})`);

                    if (apiError.errors && apiError.errors.length > 0) {
                        const validationErrors = apiError.errors.map(e => `${e.field}: ${e.message}`).join(', ');
                        setError(`${apiError.message} - ${validationErrors}`);
                    }
                } else {
                    setError(err.message || 'Failed to fetch baggage details');
                }
            } finally {
                setLoading(false);
            }
        };
        fetchBaggage();
    }, [id]);

    if (loading) return <div className="loading">Loading baggage details...</div>;
    if (error) return <div className="error">Error: {error}</div>;
    if (!baggage) return <div className="error">Baggage not found</div>;

    return (
        <div className="baggage-details-container">
            <div className="form-header">
                <button onClick={() => navigate('/baggage')} className="back-button">
                    ← Back
                </button>
                <h1>Baggage Details</h1>
            </div>

            <div className="baggage-details">
                <div className="detail-row">
                    <strong>Baggage ID:</strong>
                    <span>{baggage.id}</span>
                </div>
                <div className="detail-row">
                    <strong>Tag Number:</strong>
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

            <div className="action-buttons">
                <button
                    onClick={() => navigate(`/baggage/update-weight`)}
                    className="action-button warning"
                >
                    Update Weight
                </button>
                <button
                    onClick={() => navigate(`/baggage/update-status`)}
                    className="action-button warning"
                >
                    Update Status
                </button>
            </div>
        </div>
    );
};

export default BaggageDetails;