import React from 'react';
import { useForm } from 'react-hook-form';
import {type FlightSearchRequest, FlightStatus } from '../types/flight';
import './FlightSearch.css'

interface FlightSearchProps {
    onSearch: (searchParams: FlightSearchRequest) => void;
    onClear: () => void;
}

const FlightSearch: React.FC<FlightSearchProps> = ({ onSearch, onClear }) => {
    const { register, handleSubmit, reset } = useForm<FlightSearchRequest>();

    const onSubmit = (data: FlightSearchRequest) => {
        onSearch(data);
    };

    const handleClear = () => {
        reset();
        onClear();
    };

    return (
        <div className="flight-search">
            <h3>Search Flights</h3>
            <form onSubmit={handleSubmit(onSubmit)} className="search-form">
                <div className="search-row">
                    <input
                        type="text"
                        placeholder="Flight Number"
                        {...register('flightNumber')}
                    />
                    <input
                        type="text"
                        placeholder="Airline"
                        {...register('airline')}
                    />
                    <input
                        type="text"
                        placeholder="Origin"
                        {...register('origin')}
                    />
                </div>

                <div className="search-row">
                    <input
                        type="text"
                        placeholder="Destination"
                        {...register('destination')}
                    />
                    <select {...register('status')}>
                        <option value="">All Statuses</option>
                        {Object.values(FlightStatus).map(status => (
                            <option key={status} value={status}>{status}</option>
                        ))}
                    </select>
                    <input
                        type="date"
                        {...register('departureDate')}
                    />
                </div>

                <div className="search-actions">
                    <button type="submit">Search</button>
                    <button type="button" onClick={handleClear}>Clear</button>
                </div>
            </form>
        </div>
    );
};

export default FlightSearch;