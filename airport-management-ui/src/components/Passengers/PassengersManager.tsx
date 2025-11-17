import React, {useState, useEffect} from 'react';
import {useNavigate, useParams, useLocation} from 'react-router-dom';
import passengerService, {type ApiErrorResponse} from '../../services/passengerService.ts';
import PassengerForm from './PassengerForm.tsx';
import PassengerList from './PassengerList.tsx';
import PassengerSearch from './PassengerSearch.tsx';
import type {PassengerRequest, PassengerSearchRequest, PassengerResponse} from '../../types/passenger.ts';
import './PassengersManager.css';

const PassengersManager: React.FC = () => {
    const [passengers, setPassengers] = useState<PassengerResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [formError, setFormError] = useState<ApiErrorResponse | null>(null);
    const [formLoading, setFormLoading] = useState(false);
    const [isSearching, setIsSearching] = useState(false);

    const navigate = useNavigate();
    const params = useParams();
    const location = useLocation();

    const isViewingDetails = params.passengerId && location.pathname === `/passengers/${params.passengerId}`;
    const isCreating = location.pathname === '/passengers/create';
    const isEditing = params.passengerId && location.pathname === `/passengers/${params.passengerId}/edit`;

    useEffect(() => {
        if (!isSearching && !isViewingDetails && !isCreating && !isEditing) {
            loadPassengers();
        }
    }, [isSearching, isViewingDetails, isCreating, isEditing]);

    const loadPassengers = async () => {
        try {
            setLoading(true);
            const data = await passengerService.getPassengers();
            setPassengers(data);
            setError(null);
        } catch (err) {
            setError('Failed to load passengers');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = async (searchParams: PassengerSearchRequest) => {
        try {
            setLoading(true);
            setIsSearching(true);
            const data = await passengerService.searchPassengers(searchParams);
            setPassengers(data);
            setError(null);
        } catch (err) {
            setError('Failed to search passengers');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleClearSearch = () => {
        setIsSearching(false);
        loadPassengers();
    };

    const handleCreatePassenger = async (passengerData: PassengerRequest) => {
        try {
            setFormLoading(true);
            setFormError(null);
            await passengerService.createPassenger(passengerData);
            setFormError(null);
            navigate('/passengers');
            loadPassengers();
        } catch (err) {
            const apiError = err as ApiErrorResponse;
            setFormError(apiError);
            if (!apiError.errors || apiError.errors.length === 0) {
                setError(`Failed to create passenger: ${apiError.message}`);
            }
            throw err;
        } finally {
            setFormLoading(false);
        }
    };

    const handleUpdatePassenger = async (passengerData: PassengerRequest) => {
        if (!params.passengerId) return;

        try {
            setFormLoading(true);
            setFormError(null);
            await passengerService.updatePassenger(params.passengerId, passengerData);
            setFormError(null);
            navigate('/passengers');
            loadPassengers();
        } catch (err) {
            const apiError = err as ApiErrorResponse;
            setFormError(apiError);
            if (!apiError.errors || apiError.errors.length === 0) {
                setError(`Failed to update passenger: ${apiError.message}`);
            }
            throw err;
        } finally {
            setFormLoading(false);
        }
    };

    const handleDeletePassenger = async (id: string) => {
        if (!window.confirm('Are you sure you want to delete this passenger?')) return;

        try {
            await passengerService.deletePassenger(id);
            loadPassengers();
        } catch (err) {
            setError('Failed to delete passenger');
            console.error(err);
        }
    };


    const handleCreatePassengerNavigate = () => {
        navigate('/passengers/create');
    };

    const handleEditPassenger = (passenger: PassengerResponse) => {
        navigate(`/passengers/${passenger.id}/edit`);
    };

    const handleBackToList = () => {
        navigate('/passengers');
    };

    if (isCreating || isEditing) {
        const editingPassenger = isEditing && params.passengerId
            ? passengers.find(p => p.id === params.passengerId) || null
            : null;

        return (
            <div className="passengers-container">
                <button onClick={handleBackToList} className="back-button">
                    ← Back to List
                </button>
                <PassengerForm
                    onSubmit={editingPassenger ? handleUpdatePassenger : handleCreatePassenger}
                    onCancel={handleBackToList}
                    initialData={editingPassenger || undefined}
                    isEditing={!!editingPassenger}
                    serverError={formError}
                    loading={formLoading}
                />
            </div>
        );
    }

    return (
        <div className="passengers-container">
            <div className="page-header">
                <button onClick={() => navigate('/')} className="back-to-home">
                    ← Back to Home
                </button>
            </div>
            <div className="passengers-header">
                <h1>Passengers Management</h1>
                <button
                    onClick={handleCreatePassengerNavigate}
                    className="create-button"
                >
                    + Create New Passenger
                </button>
            </div>

            <PassengerSearch onSearch={handleSearch} onClear={handleClearSearch}/>

            {loading && <div className="loading">Loading passengers...</div>}
            {error && (
                <div className="error">
                    Error: {error}
                    <button onClick={loadPassengers}>Retry</button>
                </div>
            )}

            <PassengerList
                passengers={passengers}
                onEdit={handleEditPassenger}
                onDelete={handleDeletePassenger}
                loading={loading}
            />
        </div>
    );
};

export default PassengersManager;