import React from 'react';
import {type PassengerResponse, Gender} from '../../types/passenger.ts';
import './PassengerList.css';

interface PassengerListProps {
    passengers: PassengerResponse[];
    onEdit: (passenger: PassengerResponse) => void;
    onDelete: (id: string) => void;
    loading?: boolean;
}

const PassengerList: React.FC<PassengerListProps> = ({
                                                         passengers,
                                                         onEdit,
                                                         onDelete,
                                                         loading = false
                                                     }) => {
    const formatDate = (dateString: string) => {
        return new Date(dateString).toLocaleDateString();
    };

    const calculateAge = (dateOfBirth: string) => {
        const today = new Date();
        const birthDate = new Date(dateOfBirth);
        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDiff = today.getMonth() - birthDate.getMonth();

        if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }

        return age;
    };

    const getGenderColor = (gender: Gender) => {
        switch (gender) {
            case Gender.MALE:
                return '#4f46e5';
            case Gender.FEMALE:
                return '#ec4899';
            default:
                return '#6b7280';
        }
    };

    if (loading) {
        return <div className="loading">Loading passengers...</div>;
    }

    if (passengers.length === 0) {
        return <div className="no-passengers">No passengers found</div>;
    }

    return (
        <div className="passenger-list">
            {passengers.map((passenger) => (
                <div key={passenger.id} className="passenger-card">
                    <div className="passenger-card-header">
                        <h3 className="passenger-name">
                            {passenger.firstName} {passenger.middleName ? passenger.middleName + ' ' : ''}{passenger.lastName}
                        </h3>
                        <div className="passenger-card-actions">
                            <button
                                onClick={() => onEdit(passenger)}
                                className="edit-button"
                                title="Edit passenger"
                            >
                                Edit
                            </button>
                            <button
                                onClick={() => onDelete(passenger.id)}
                                className="delete-button"
                                title="Delete passenger"
                            >
                                ×
                            </button>
                        </div>
                    </div>

                    <div className="passenger-info">
                        <div className="info-section">
                            <p><strong>Gender:</strong>
                                <span style={{color: getGenderColor(passenger.gender)}}>
                                    {passenger.gender}
                                </span>
                            </p>
                            <p><strong>Age:</strong> {calculateAge(passenger.dateOfBirth)} years</p>
                            <p><strong>Date of Birth:</strong> {formatDate(passenger.dateOfBirth)}</p>
                        </div>

                        <div className="info-section">
                            <p><strong>Passport:</strong> {passenger.passportNumber}</p>
                            <p><strong>Country:</strong> {passenger.passportCountry}</p>
                            <p><strong>Expiry:</strong>
                                <span
                                    className={new Date(passenger.passportExpiryDate) < new Date() ? 'expired' : 'valid'}>
                                    {formatDate(passenger.passportExpiryDate)}
                                </span>
                            </p>
                        </div>

                        <div className="info-section">
                            <p><strong>Nationality:</strong> {passenger.nationality}</p>
                            {passenger.email && (
                                <p className={"email-field"}>
                                    <strong>Email:</strong>
                                    <span>{passenger.email}</span>
                                </p>
                            )}
                            {passenger.phoneNumber && <p><strong>Phone:</strong> {passenger.phoneNumber}</p>}
                        </div>

                        {passenger.address && (
                            <div className="info-section">
                                <p className="address-field">
                                    <strong>Address:</strong>
                                    <span>{passenger.address}</span>
                                </p>
                            </div>
                        )}
                    </div>
                </div>
            ))}
        </div>
    );
};

export default PassengerList;