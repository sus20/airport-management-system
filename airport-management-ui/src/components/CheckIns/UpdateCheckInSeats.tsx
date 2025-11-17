import React, {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import checkinService from "../../services/checkInService.ts";
import type {UpdateSeatRequest} from "../../types/checkIn.ts";

const UpdateCheckInSeats: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [formData, setFormData] = useState({
        checkInId: '',
        seatNumbers: ['']
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (location.state?.checkInId) {
            setFormData(prev => ({ ...prev, checkInId: location.state.checkInId }));
        }
    }, [location.state]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const seatData: UpdateSeatRequest = {
                seatNumbers: formData.seatNumbers.filter(seat => seat.trim() !== '')
            };

            await checkinService.updateSeatNumbers(formData.checkInId, seatData);
            alert('Seat numbers updated successfully!');
            navigate('/checkins');
        } catch (err: any) {
            setError(err.response?.data?.message || err.message || 'Failed to update seat numbers');
        } finally {
            setLoading(false);
        }
    };

    const addSeat = () => {
        if (formData.seatNumbers.length < 2) {
            setFormData(prev => ({
                ...prev,
                seatNumbers: [...prev.seatNumbers, '']
            }));
        }
    };

    const removeSeat = (index: number) => {
        setFormData(prev => ({
            ...prev,
            seatNumbers: prev.seatNumbers.filter((_, i) => i !== index)
        }));
    };

    const updateSeat = (index: number, value: string) => {
        setFormData(prev => ({
            ...prev,
            seatNumbers: prev.seatNumbers.map((seat, i) => i === index ? value : seat)
        }));
    };

    return (
        <div className="checkin-form-container">
            <div className="form-header">
                <button onClick={() => navigate('/checkins')} className="back-button">
                    ← Back to Check-In Manager
                </button>
                <h1>Update Seat Numbers</h1>
            </div>

            <form onSubmit={handleSubmit} className="checkin-form">
                {error && <div className="error-message">{error}</div>}

                <div className="form-group">
                    <label htmlFor="checkInId">Check-In ID *</label>
                    <input
                        type="text"
                        id="checkInId"
                        value={formData.checkInId}
                        onChange={(e) => setFormData(prev => ({...prev, checkInId: e.target.value}))}
                        required
                        placeholder="Enter check-in ID"
                    />
                </div>

                <div className="form-group">
                    <label>Seat Numbers *</label>
                    {formData.seatNumbers.map((seat, index) => (
                        <div key={index} className="input-with-button">
                            <input
                                type="text"
                                value={seat}
                                onChange={(e) => updateSeat(index, e.target.value)}
                                placeholder="Enter seat number (e.g., 12A, 1B)"
                                pattern="^[0-9]{1,2}[A-F]$"
                                required
                            />
                            {formData.seatNumbers.length > 1 && (
                                <button
                                    type="button"
                                    onClick={() => removeSeat(index)}
                                    className="remove-button"
                                >
                                    Remove
                                </button>
                            )}
                        </div>
                    ))}
                    {formData.seatNumbers.length < 2 && (
                        <button type="button" onClick={addSeat} className="add-button">
                            + Add Another Seat
                        </button>
                    )}
                    <small>Format: 1-2 numbers followed by A-F (e.g., 12A, 1B). Maximum 2 seats per passenger.</small>
                </div>

                <div className="form-actions">
                    <button type="submit" disabled={loading} className="submit-button">
                        {loading ? 'Updating...' : 'Update Seats'}
                    </button>
                    <button type="button" onClick={() => navigate('/checkins')} className="cancel-button">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default UpdateCheckInSeats;