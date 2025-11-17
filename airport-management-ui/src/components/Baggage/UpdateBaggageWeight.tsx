import React, {useState, useEffect} from 'react';
import {useNavigate, useLocation} from 'react-router-dom';
import type {BaggageRequest} from "../../types/baggage.ts";
import baggageService from "../../services/baggageService.ts";
import './Baggage.css';

const UpdateBaggageWeight: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [formData, setFormData] = useState({
        baggageId: '',
        weight: ''
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (location.state?.baggageId) {
            setFormData(prev => ({...prev, baggageId: location.state.baggageId}));
        }
    }, [location.state]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const weightData: BaggageRequest = {
                weight: parseFloat(formData.weight)
            };

            await baggageService.updateBaggageWeight(formData.baggageId, weightData);
            alert('Baggage weight updated successfully!');
            navigate('/baggage');
        } catch (err: any) {
            setError(err.response?.data?.message || err.message || 'Failed to update baggage weight');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="baggage-form-container">
            <div className="form-header">
                <button onClick={() => navigate('/baggage')} className="back-button">
                    ← Back to Baggage Manager
                </button>
                <h1>Update Baggage Weight</h1>
            </div>

            <form onSubmit={handleSubmit} className="baggage-form">
                {error && <div className="error-message">{error}</div>}

                <div className="form-group">
                    <label htmlFor="baggageId">Baggage ID *</label>
                    <input
                        type="text"
                        id="baggageId"
                        value={formData.baggageId}
                        onChange={(e) => setFormData(prev => ({...prev, baggageId: e.target.value}))}
                        required
                        placeholder="Enter baggage ID"
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="weight">New Weight (kg) *</label>
                    <input
                        type="number"
                        id="weight"
                        step="0.1"
                        min="0"
                        max="25"
                        value={formData.weight}
                        onChange={(e) => setFormData(prev => ({...prev, weight: e.target.value}))}
                        required
                        placeholder="Enter weight in kilograms (0-25kg)"
                    />
                    <small>Maximum weight: 25kg</small>
                </div>

                <div className="form-actions">
                    <button type="submit" disabled={loading} className="submit-button">
                        {loading ? 'Updating...' : 'Update Weight'}
                    </button>
                    <button type="button" onClick={() => navigate('/baggage')} className="cancel-button">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default UpdateBaggageWeight;