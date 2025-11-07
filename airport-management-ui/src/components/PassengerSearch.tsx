import React from 'react';
import { useForm } from 'react-hook-form';
import { type PassengerSearchRequest, Gender } from '../types/passenger';
import './PassengerSearch.css';

interface PassengerSearchProps {
    onSearch: (searchParams: PassengerSearchRequest) => void;
    onClear: () => void;
}

const PassengerSearch: React.FC<PassengerSearchProps> = ({ onSearch, onClear }) => {
    const { register, handleSubmit, reset } = useForm<PassengerSearchRequest>();

    const onSubmit = (data: PassengerSearchRequest) => {
        const searchParams: PassengerSearchRequest = {
            firstName: data.firstName?.trim() || undefined,
            middleName: data.middleName?.trim() || undefined,
            lastName: data.lastName?.trim() || undefined,
            gender: data.gender || undefined,
            address: data.address?.trim() || undefined,
            email: data.email?.trim() || undefined,
            phoneNumber: data.phoneNumber?.trim() || undefined,
            passportNumber: data.passportNumber?.trim() || undefined,
            nationality: data.nationality?.trim() || undefined,
        };
        onSearch(searchParams);
    };

    const handleClear = () => {
        reset();
        onClear();
    };

    return (
        <div className="passenger-search">
            <h3>Search Passengers</h3>
            <form onSubmit={handleSubmit(onSubmit)} className="search-form">
                <div className="search-section">
                    <h4>Personal Information</h4>
                    <div className="search-row">
                        <div className="form-group">
                            <label>First Name</label>
                            <input
                                type="text"
                                placeholder="John"
                                {...register('firstName')}
                            />
                        </div>

                        <div className="form-group">
                            <label>Middle Name</label>
                            <input
                                type="text"
                                placeholder="Michael"
                                {...register('middleName')}
                            />
                        </div>

                        <div className="form-group">
                            <label>Last Name</label>
                            <input
                                type="text"
                                placeholder="Doe"
                                {...register('lastName')}
                            />
                        </div>

                        <div className="form-group">
                            <label>Gender</label>
                            <select {...register('gender')}>
                                <option value="">All Genders</option>
                                <option value={Gender.MALE}>Male</option>
                                <option value={Gender.FEMALE}>Female</option>
                                <option value={Gender.OTHER}>Other</option>
                                <option value={Gender.UNSPECIFIED}>Unspecified</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div className="search-section">
                    <h4>Contact Information</h4>
                    <div className="search-row">
                        <div className="form-group">
                            <label>Email</label>
                            <input
                                type="email"
                                placeholder="john.doe@example.com"
                                {...register('email')}
                            />
                        </div>

                        <div className="form-group">
                            <label>Phone Number</label>
                            <input
                                type="tel"
                                placeholder="+1234567890"
                                {...register('phoneNumber')}
                            />
                        </div>

                        <div className="form-group">
                            <label>Address</label>
                            <input
                                type="text"
                                placeholder="123 Main St"
                                {...register('address')}
                            />
                        </div>
                    </div>
                </div>

                <div className="search-section">
                    <h4>Passport Information</h4>
                    <div className="search-row">
                        <div className="form-group">
                            <label>Passport Number</label>
                            <input
                                type="text"
                                placeholder="AB123456"
                                {...register('passportNumber')}
                            />
                        </div>

                        <div className="form-group">
                            <label>Nationality</label>
                            <input
                                type="text"
                                placeholder="American"
                                {...register('nationality')}
                            />
                        </div>
                    </div>
                </div>

                <div className="search-actions">
                    <button type="submit" className="search-button">
                        Search Passengers
                    </button>
                    <button type="button" onClick={handleClear} className="clear-button">
                        Clear All
                    </button>
                </div>
            </form>
        </div>
    );
};

export default PassengerSearch;