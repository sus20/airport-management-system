import React, {useState, useEffect} from 'react';
import {useNavigate, useParams, useLocation} from 'react-router-dom';
import flightService, {type ApiErrorResponse} from '../../services/flightService.ts';
import FlightForm from './FlightForm.tsx';
import FlightDetails from './FlightDetails.tsx';
import FlightSearch from './FlightSearch.tsx';
import type {Flight, FlightRequest, FlightSearchRequest, PaginationParams} from '../../types/flight.ts';
import './FlightsManager.css';

const FlightsManager: React.FC = () => {
    const [flights, setFlights] = useState<Flight[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [pagination, setPagination] = useState<PaginationParams>({page: 0, size: 5});
    const [isSearching, setIsSearching] = useState(false);
    const [formError, setFormError] = useState<ApiErrorResponse | null>(null);
    const [formLoading, setFormLoading] = useState(false);

    const navigate = useNavigate();
    const params = useParams();
    const location = useLocation();

    const isViewingDetails = params.flightId && location.pathname === `/flights/${params.flightId}`;
    const isCreating = location.pathname === '/flights/create';
    const isEditing = params.flightId && location.pathname === `/flights/${params.flightId}/edit`;

    useEffect(() => {
        if (!isSearching && !isViewingDetails && !isCreating && !isEditing) {
            loadFlights();
        }
    }, [pagination.page, pagination.size, isSearching, isViewingDetails, isCreating, isEditing]);

    const loadFlights = async () => {
        try {
            setLoading(true);
            const data = await flightService.getFlights(pagination);
            setFlights(data);
            setError(null);
        } catch (err) {
            setError('Failed to load flights');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = async (searchParams: FlightSearchRequest) => {
        try {
            setLoading(true);
            setIsSearching(true);
            const data = await flightService.searchFlights(searchParams);
            setFlights(data);
            setError(null);
        } catch (err) {
            setError('Failed to search flights');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleClearSearch = () => {
        setIsSearching(false);
        setPagination(prev => ({...prev, page: 0}));
        loadFlights();
    };

    const handleCreateFlight = async (flightData: FlightRequest) => {
        try {
            setFormLoading(true);
            setFormError(null);

            await flightService.createFlight(flightData);
            setFormError(null);
            navigate('/flights');
            loadFlights(); // Refresh the list
        } catch (err) {
            const apiError = err as ApiErrorResponse;
            setFormError(apiError);

            if (!apiError.errors || apiError.errors.length === 0) {
                setError(`Failed to create flight: ${apiError.message}`);
            }

            throw err;
        } finally {
            setFormLoading(false);
        }
    };

    const handleUpdateFlight = async (flightData: FlightRequest) => {
        if (!params.flightId) return;

        try {
            setFormLoading(true);
            setFormError(null);

            await flightService.updateFlight(params.flightId, flightData);
            setFormError(null);
            navigate('/flights');
            loadFlights();
        } catch (err) {
            const apiError = err as ApiErrorResponse;
            setFormError(apiError);

            if (!apiError.errors || apiError.errors.length === 0) {
                setError(`Failed to update flight: ${apiError.message}`);
            }

            throw err;
        } finally {
            setFormLoading(false);
        }
    };

    const handleDeleteFlight = async (id: string) => {
        if (!window.confirm('Are you sure you want to delete this flight?')) return;

        try {
            await flightService.deleteFlight(id);
            loadFlights();
        } catch (err) {
            setError('Failed to delete flight');
            console.error(err);
        }
    };

    const handleFlightUpdate = (updatedFlight: Flight) => {
        setFlights(prev => prev.map(f => f.id === updatedFlight.id ? updatedFlight : f));
    };

    const handleViewFlight = (flightId: string) => {
        navigate(`/flights/${flightId}`);
    };

    const handleCreateFlightNavigate = () => {
        navigate('/flights/create');
    };

    const handleEditFlight = (flight: Flight) => {
        navigate(`/flights/${flight.id}/edit`);
    };

    const handleBackToList = () => {
        navigate('/flights');
    };

    const handlePrevious = () => {
        if (pagination.page > 0) {
            setPagination(prev => ({...prev, page: prev.page - 1}));
        }
    };

    const handleNext = () => {
        setPagination(prev => ({...prev, page: prev.page + 1}));
    };

    useEffect(() => {
        if (isCreating || isEditing) {
            setFormError(null);
        }
    }, [isCreating, isEditing]);

    if (isViewingDetails && params.flightId) {
        return (
            <FlightDetails
                flightId={params.flightId}
                onBack={handleBackToList}
                onFlightUpdate={handleFlightUpdate}
            />
        );
    }

    if (isCreating || isEditing) {
        // Find the flight being edited
        const editingFlight = isEditing && params.flightId
            ? flights.find(f => f.id === params.flightId) || null
            : null;

        return (
            <div className="flights-container">
                <button onClick={handleBackToList} className="back-button">
                    ← Back to List
                </button>
                <FlightForm
                    onSubmit={editingFlight ? handleUpdateFlight : handleCreateFlight}
                    onCancel={handleBackToList}
                    initialData={editingFlight || undefined}
                    isEditing={!!editingFlight}
                    serverError={formError}
                    loading={formLoading}
                />
            </div>
        );
    }


    return (

        <div className="flights-container">
            <div className="page-header">
                <button onClick={() => navigate('/')} className="back-to-home">
                    ← Back to Home
                </button>
            </div>

            <div className="flights-header">
                <h1>Flights Management</h1>
                <button
                    onClick={handleCreateFlightNavigate}
                    className="create-button"
                >
                    + Create New Flight
                </button>
            </div>

            <FlightSearch onSearch={handleSearch} onClear={handleClearSearch}/>

            <div className="pagination-controls">
                <button onClick={handlePrevious} disabled={pagination.page === 0}>
                    Previous
                </button>
                <span>Page {pagination.page + 1}</span>
                <button onClick={handleNext}>
                    Next
                </button>
                <select
                    value={pagination.size}
                    onChange={(e) => setPagination(prev => ({...prev, size: Number(e.target.value), page: 0}))}
                >
                    <option value="5">5 per page</option>
                    <option value="10">10 per page</option>
                    <option value="20">20 per page</option>
                </select>
            </div>

            {loading && <div className="loading">Loading flights...</div>}
            {error && (
                <div className="error">
                    Error: {error}
                    <button onClick={loadFlights}>Retry</button>
                </div>
            )}

            <div className="flights-list">
                {flights.length === 0 ? (
                    <div className="no-flights">No flights found</div>
                ) : (
                    flights.map((flight) => (
                        <div key={flight.id} className="flight-card">
                            <div className="flight-card-header">
                                <h3 onClick={() => handleViewFlight(flight.id)} className="flight-title">
                                    {flight.airline} {flight.flightNumber}
                                </h3>
                                <div className="flight-card-actions">
                                    <button
                                        onClick={() => handleEditFlight(flight)}
                                        className="edit-button"
                                        title="Edit flight"
                                    >
                                        Edit
                                    </button>
                                    <button
                                        onClick={() => handleDeleteFlight(flight.id)}
                                        className="delete-button"
                                        title="Delete flight"
                                    >
                                        ×
                                    </button>
                                </div>
                            </div>

                            <p><strong>Route:</strong> {flight.origin} → {flight.destination}</p>
                            <p><strong>Departure:</strong> {new Date(flight.departureTime).toLocaleString()}</p>
                            <p><strong>Arrival:</strong> {new Date(flight.arrivalTime).toLocaleString()}</p>
                            <p><strong>Status:</strong>
                                <span className={`status ${flight.status.toLowerCase()}`}>
                                    {flight.status}
                                </span>
                            </p>
                            <p><strong>Aircraft:</strong> {flight.aircraftType}</p>
                            <p><strong>Gate:</strong> {flight.gate} (Terminal {flight.terminal})</p>
                            <p><strong>Price:</strong> ${flight.price}</p>
                        </div>
                    ))
                )}
            </div>
        </div>
    )
        ;
};

export default FlightsManager;