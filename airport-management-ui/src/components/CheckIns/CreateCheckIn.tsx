import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import type {FlightCheckInRequest} from "../../types/checkIn.ts";
import checkinService from "../../services/checkInService.ts";
import './CheckIn.css';

const CreateCheckIn: React.FC = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        flightNumber: '',
        passportNumber: '',
        seatNumbers: [''],
        baggages: [{ weight: 0 }]
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const checkInData: FlightCheckInRequest = {
                flightNumber: formData.flightNumber,
                passportNumber: formData.passportNumber,
                seatNumbers: formData.seatNumbers.filter(seat => seat.trim() !== ''),
                baggages: formData.baggages.filter(baggage => baggage.weight > 0)
            };

            await checkinService.createCheckIn(checkInData);
            alert('Check-in created successfully!');
            navigate('/checkins');
        } catch (err: any) {
            setError(err.response?.data?.message || err.message || 'Failed to create check-in');
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

    const addBaggage = () => {
        setFormData(prev => ({
            ...prev,
            baggages: [...prev.baggages, { weight: 0 }]
        }));
    };

    const removeBaggage = (index: number) => {
        setFormData(prev => ({
            ...prev,
            baggages: prev.baggages.filter((_, i) => i !== index)
        }));
    };

    const updateBaggageWeight = (index: number, weight: number) => {
        setFormData(prev => ({
            ...prev,
            baggages: prev.baggages.map((baggage, i) => i === index ? { ...baggage, weight } : baggage)
        }));
    };

    return (
        <div className="checkin-form-container">
            <div className="form-header">
                <button onClick={() => navigate('/checkins')} className="back-button">
                    ← Back to Check-In Manager
                </button>
                <h1>Create New Check-In</h1>
            </div>

            <form onSubmit={handleSubmit} className="checkin-form">
                {error && <div className="error-message">{error}</div>}

                <div className="form-group">
                    <label htmlFor="flightNumber">Flight Number *</label>
                    <input
                        type="text"
                        id="flightNumber"
                        value={formData.flightNumber}
                        onChange={(e) => setFormData(prev => ({...prev, flightNumber: e.target.value}))}
                        required
                        placeholder="Enter flight number (e.g., EK101, LH789)"
                        pattern="^[A-Z0-9]{2,3}[0-9]{1,4}$"
                    />
                    <small>Format: 2-3 letters followed by 1-4 numbers (e.g., EK101, LH789)</small>
                </div>

                <div className="form-group">
                    <label htmlFor="passportNumber">Passport Number *</label>
                    <input
                        type="text"
                        id="passportNumber"
                        value={formData.passportNumber}
                        onChange={(e) => setFormData(prev => ({...prev, passportNumber: e.target.value}))}
                        required
                        placeholder="Enter passport number"
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

                <div className="form-group">
                    <label>Baggage Information</label>
                    {formData.baggages.map((baggage, index) => (
                        <div key={index} className="input-with-button">
                            <input
                                type="number"
                                step="0.1"
                                min="0"
                                max="25"
                                value={baggage.weight}
                                onChange={(e) => updateBaggageWeight(index, parseFloat(e.target.value) || 0)}
                                placeholder="Weight in kg (0-25)"
                            />
                            {formData.baggages.length > 1 && (
                                <button
                                    type="button"
                                    onClick={() => removeBaggage(index)}
                                    className="remove-button"
                                >
                                    Remove
                                </button>
                            )}
                        </div>
                    ))}
                    <button type="button" onClick={addBaggage} className="add-button">
                        + Add Baggage
                    </button>
                    <small>Maximum weight: 25kg per baggage. Leave weight as 0 to skip baggage.</small>
                </div>

                <div className="form-actions">
                    <button type="submit" disabled={loading} className="submit-button">
                        {loading ? 'Creating...' : 'Create Check-In'}
                    </button>
                    <button type="button" onClick={() => navigate('/checkins')} className="cancel-button">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
};

export default CreateCheckIn;