import React from 'react';
import {useForm} from 'react-hook-form';
import {type FlightSearchRequest} from '../types/flight';
import './FlightSearch.css';

interface FlightSearchProps {
    onSearch: (searchParams: FlightSearchRequest) => void;
    onClear: () => void;
}

const FlightSearch: React.FC<FlightSearchProps> = ({onSearch, onClear}) => {
    const {register, handleSubmit, reset, watch, formState: {errors}} = useForm<FlightSearchRequest>();

    const onSubmit = (data: FlightSearchRequest) => {
        const searchParams: FlightSearchRequest = {
            flightNumber: data.flightNumber?.trim() || undefined,
            airline: data.airline?.trim() || undefined,
            origin: data.origin?.trim().toUpperCase() || undefined,
            destination: data.destination?.trim().toUpperCase() || undefined,
            aircraftType: data.aircraftType?.trim() || undefined,
            departureFrom: data.departureFrom || undefined,
            departureTo: data.departureTo || undefined,
            arrivalFrom: data.arrivalFrom || undefined,
            arrivalTo: data.arrivalTo || undefined,
            priceMin: data.priceMin ? Number(data.priceMin) : undefined,
            priceMax: data.priceMax ? Number(data.priceMax) : undefined,
        };

        if (searchParams.departureFrom && searchParams.departureTo) {
            if (new Date(searchParams.departureFrom) > new Date(searchParams.departureTo)) {
                alert('Departure "From" date cannot be after "To" date');
                return;
            }
        }

        if (searchParams.arrivalFrom && searchParams.arrivalTo) {
            if (new Date(searchParams.arrivalFrom) > new Date(searchParams.arrivalTo)) {
                alert('Arrival "From" date cannot be after "To" date');
                return;
            }
        }

        if (searchParams.priceMin && searchParams.priceMax) {
            if (searchParams.priceMin > searchParams.priceMax) {
                alert('Minimum price cannot be greater than maximum price');
                return;
            }
        }

        onSearch(searchParams);
    };

    const handleClear = () => {
        reset();
        onClear();
    };

    const watchOrigin = watch('origin');
    watch('destination');
    return (
        <div className="flight-search">
            <h3>Search Flights</h3>
            <form onSubmit={handleSubmit(onSubmit)} className="search-form">
                <div className="search-section">
                    <h4>Flight Information</h4>
                    <div className="search-row">
                        <div className="form-group">
                            <label>Flight Number</label>
                            <input
                                type="text"
                                placeholder="EK101"
                                {...register('flightNumber', {
                                    pattern: {
                                        value: /^[A-Z0-9]{0,10}$/,
                                        message: 'Invalid flight number format'
                                    }
                                })}
                                className={errors.flightNumber ? 'error' : ''}
                            />
                            {errors.flightNumber && (
                                <span className="error-message">{errors.flightNumber.message}</span>
                            )}
                        </div>

                        <div className="form-group">
                            <label>Airline</label>
                            <input
                                type="text"
                                placeholder="Emirates"
                                {...register('airline')}
                            />
                        </div>

                        <div className="form-group">
                            <label>Origin</label>
                            <input
                                type="text"
                                placeholder="DXB"
                                {...register('origin', {
                                    pattern: {
                                        value: /^[A-Z]{0,3}$/,
                                        message: 'Must be 3-letter IATA code'
                                    },
                                    maxLength: 3
                                })}
                                className={errors.origin ? 'error' : ''}
                            />
                            {errors.origin && (
                                <span className="error-message">{errors.origin.message}</span>
                            )}
                        </div>

                        <div className="form-group">
                            <label>Destination</label>
                            <input
                                type="text"
                                placeholder="JFK"
                                {...register('destination', {
                                    pattern: {
                                        value: /^[A-Z]{0,3}$/,
                                        message: 'Must be 3-letter IATA code'
                                    },
                                    maxLength: 3,
                                    validate: (value) =>
                                        !value || !watchOrigin || value !== watchOrigin ||
                                        'Destination must be different from origin'
                                })}
                                className={errors.destination ? 'error' : ''}
                            />
                            {errors.destination && (
                                <span className="error-message">{errors.destination.message}</span>
                            )}
                        </div>
                    </div>
                </div>

                {/* Aircraft Information */}
                <div className="search-section">
                    <h4>Aircraft Information</h4>
                    <div className="search-row">
                        <div className="form-group full-width">
                            <label>Aircraft Type</label>
                            <input
                                type="text"
                                placeholder="Airbus A380, Boeing 737, etc."
                                {...register('aircraftType')}
                            />
                        </div>
                    </div>
                </div>

                <div className="search-section">
                    <h4>Time Range</h4>
                    <div className="search-row">
                        <div className="form-group">
                            <label>Departure From</label>
                            <input
                                type="datetime-local"
                                {...register('departureFrom')}
                            />
                        </div>
                        <div className="form-group">
                            <label>Departure To</label>
                            <input
                                type="datetime-local"
                                {...register('departureTo')}
                            />
                        </div>
                    </div>

                    <div className="search-row">
                        <div className="form-group">
                            <label>Arrival From</label>
                            <input
                                type="datetime-local"
                                {...register('arrivalFrom')}
                            />
                        </div>
                        <div className="form-group">
                            <label>Arrival To</label>
                            <input
                                type="datetime-local"
                                {...register('arrivalTo')}
                            />
                        </div>
                    </div>
                </div>

                <div className="search-section">
                    <h4>Price Range ($)</h4>
                    <div className="search-row">
                        <div className="form-group">
                            <label>Minimum Price</label>
                            <input
                                type="number"
                                step="0.01"
                                min="0"
                                placeholder="0.00"
                                {...register('priceMin', {
                                    min: {
                                        value: 0,
                                        message: 'Price must be positive'
                                    }
                                })}
                                className={errors.priceMin ? 'error' : ''}
                            />
                            {errors.priceMin && (
                                <span className="error-message">{errors.priceMin.message}</span>
                            )}
                        </div>
                        <div className="form-group">
                            <label>Maximum Price</label>
                            <input
                                type="number"
                                step="0.01"
                                min="0"
                                placeholder="0.00"
                                {...register('priceMax', {
                                    min: {
                                        value: 0,
                                        message: 'Price must be positive'
                                    }
                                })}
                                className={errors.priceMax ? 'error' : ''}
                            />
                            {errors.priceMax && (
                                <span className="error-message">{errors.priceMax.message}</span>
                            )}
                        </div>
                    </div>
                </div>

                <div className="search-actions">
                    <button type="submit" className="search-button">
                        Search Flights
                    </button>
                    <button type="button" onClick={handleClear} className="clear-button">
                        Clear All
                    </button>
                </div>
            </form>
        </div>
    );
};

export default FlightSearch;