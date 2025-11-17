import React, {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import baggageService from "../../services/baggageService.ts";
import type {BaggageStatusUpdateRequest, BaggageStatus} from "../../types/baggage.ts";

const UpdateBaggageStatus: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [formData, setFormData] = useState({
        baggageId: '',
        status: '' as BaggageStatus
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (location.state?.baggageId) {
            setFormData(prev => ({ ...prev, baggageId: location.state.baggageId }));
        }
    }, [location.state]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const statusData: BaggageStatusUpdateRequest = {
                status: formData.status
            };

            await baggageService.updateBaggageStatus(formData.baggageId, statusData);
            alert('Baggage status updated successfully!');
            navigate('/baggage');
        } catch (err: any) {
            setError(err.response?.data?.message || err.message || 'Failed to update baggage status');
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
                <h1>Update Baggage Status</h1>
            </div>

            <form onSubmit={handleSubmit} className="baggage-form">
                {error && <div className="error-message">{error}</div>}

                <div className="form-group">
                    <label htmlFor="baggageId">Baggage ID *</label>
                    <input
                        type="text"
                        id="baggageId"
                        value={formData.baggageId}
                        onChange={(e) => setFormData(prev => ({
                            ...prev,
                            baggageId: e.target.value
                        }))}
                        required
                        placeholder="Enter baggage ID"
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="status">New Status *</label>
                    <select
                        id="status"
                        value={formData.status}
                        onChange={(e) => setFormData(prev => ({
                            ...prev,
                            status: e.target.value as BaggageStatus
                        }))}
                        required
                    >
                        <option value="">Select Status</option>
                        <option value="CHECKED_IN">Checked In</option>
                        <option value="SCREENED">Screened</option>
                        <option value="LOADED">Loaded</option>
                        <option value="IN_TRANSIT">In Transit</option>
                        <option value="ARRIVED">Arrived</option>
                        <option value="DELIVERED">Delivered</option>
                        <option value="OFFLOADED">Offloaded</option>
                        <option value="LOST">Lost</option>
                    </select>
                </div>

                <div className="form-actions">
                    <button type="submit" disabled={loading} className="submit-button">
                        {loading ? 'Updating...' : 'Update Status'}
                    </button>
                    <button type="button" onClick={() => navigate('/baggage')} className="cancel-button">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default UpdateBaggageStatus;