import React, {useEffect} from 'react';
import {useForm} from 'react-hook-form';
import {type FlightRequest, FlightStatus} from "../types/flight";
import type {ApiErrorResponse, ValidationError} from "../services/flightService";

import './FlightForm.css'

interface FlightFormProps {
    onSubmit: (data: FlightRequest) => Promise<void>;
    onCancel?: () => void;
    initialData?: Partial<FlightRequest>;
    isEditing?: boolean;
    serverError?: ApiErrorResponse | null;
    loading?: boolean;
}

const FlightForm: React.FC<FlightFormProps> = ({
                                                   onSubmit,
                                                   onCancel,
                                                   initialData,
                                                   isEditing = false,
                                                   serverError,
                                                   loading = false
                                               }) => {
    const {
        register,
        handleSubmit,
        formState: {errors, isSubmitting},
        setError,
        clearErrors
    } = useForm<FlightRequest>({
        defaultValues: initialData
    });

    useEffect(() => {
        if (serverError?.errors) {
            clearErrors();

            serverError.errors.forEach((validationError: ValidationError) => {
                const fieldName = mapServerFieldToFormField(validationError.field);
                setError(fieldName as any, {
                    type: 'server',
                    message: validationError.message
                });
            });
        }
    }, [serverError, setError, clearErrors]);

    const mapServerFieldToFormField = (serverField: string): string => {
        const fieldMap: { [key: string]: string } = {
            'flightNumber': 'flightNumber',
            'airline': 'airline',
            'origin': 'origin',
            'destination': 'destination',
            'departureTime': 'departureTime',
            'arrivalTime': 'arrivalTime',
            'aircraftType': 'aircraftType',
            'status': 'status',
            'gate': 'gate',
            'terminal': 'terminal',
            'price': 'price'
        };

        return fieldMap[serverField] || serverField;
    };

    const handleFormSubmit = async (data: FlightRequest) => {
        try {
            clearErrors();

            const submitData: FlightRequest = {
                flightNumber: data.flightNumber,
                airline: data.airline,
                origin: data.origin,
                destination: data.destination,
                departureTime: data.departureTime,
                arrivalTime: data.arrivalTime,
                aircraftType: data.aircraftType,
                price: data.price,

                ...(data.status && {status: data.status}),
                ...(data.gate && data.gate.trim() !== '' && {gate: data.gate}),
                ...(data.terminal && data.terminal.trim() !== '' && {terminal: data.terminal})
            };

            await onSubmit(submitData);
        } catch (error) {
            console.error('Form submission error:', error);
        }
    };

    const hasGeneralServerError = serverError && (!serverError.errors || serverError.errors.length === 0);

    return (
        <form onSubmit={handleSubmit(handleFormSubmit)} className="flight-form">
            <h2>{isEditing ? 'Edit Flight' : 'Create New Flight'}</h2>

            {hasGeneralServerError && (
                <div className="server-error general-error">
                    <strong>Error:</strong> {serverError.message}
                </div>
            )}

            <div className="form-row">
                <div className="form-group">
                    <label>Flight Number *</label>
                    <input
                        type="text"
                        placeholder="EK101"
                        {...register('flightNumber', {
                            required: 'Flight number is required',
                            pattern: {
                                value: /^[A-Z0-9]{2,3}[0-9]{1,4}$/,
                                message: 'Flight number must be like EK101 or LH789'
                            }
                        })}
                        className={errors.flightNumber ? 'error' : ''}
                    />
                    {errors.flightNumber && (
                        <span className="error-message">{errors.flightNumber.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Airline *</label>
                    <input
                        type="text"
                        placeholder="Emirates"
                        {...register('airline', {
                            required: 'Airline name is required'
                        })}
                        className={errors.airline ? 'error' : ''}
                    />
                    {errors.airline && (
                        <span className="error-message">{errors.airline.message}</span>
                    )}
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label>Origin *</label>
                    <input
                        type="text"
                        placeholder="DXB"
                        {...register('origin', {
                            required: 'Origin airport code is required',
                            pattern: {
                                value: /^[A-Z]{3}$/,
                                message: 'Origin must be a 3-letter IATA code'
                            },
                            maxLength: {
                                value: 3,
                                message: 'Origin must be a 3-letter IATA code'
                            },
                            minLength: {
                                value: 3,
                                message: 'Origin must be a 3-letter IATA code'
                            }
                        })}
                        className={errors.origin ? 'error' : ''}
                    />
                    {errors.origin && (
                        <span className="error-message">{errors.origin.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Destination *</label>
                    <input
                        type="text"
                        placeholder="JFK"
                        {...register('destination', {
                            required: 'Destination airport code is required',
                            pattern: {
                                value: /^[A-Z]{3}$/,
                                message: 'Destination must be a 3-letter IATA code'
                            },
                            maxLength: {
                                value: 3,
                                message: 'Destination must be a 3-letter IATA code'
                            },
                            minLength: {
                                value: 3,
                                message: 'Destination must be a 3-letter IATA code'
                            },
                            validate: (value, formValues) =>
                                value !== formValues.origin || 'Destination must be different from origin'
                        })}
                        className={errors.destination ? 'error' : ''}
                    />
                    {errors.destination && (
                        <span className="error-message">{errors.destination.message}</span>
                    )}
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label>Departure Time *</label>
                    <input
                        type="datetime-local"
                        {...register('departureTime', {
                            required: 'Departure time is required',
                            validate: {
                                notInPast: (value) => {
                                    if (new Date(value) < new Date()) {
                                        return 'Departure time must be in the future';
                                    }
                                    return true;
                                },
                                beforeArrival: (value, formValues) => {
                                    if (formValues.arrivalTime && new Date(value) >= new Date(formValues.arrivalTime)) {
                                        return 'Departure time must be before arrival time';
                                    }
                                    return true;
                                }
                            }
                        })}
                        className={errors.departureTime ? 'error' : ''}
                    />
                    {errors.departureTime && (
                        <span className="error-message">{errors.departureTime.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Arrival Time *</label>
                    <input
                        type="datetime-local"
                        {...register('arrivalTime', {
                            required: 'Arrival time is required',
                            validate: {
                                notInPast: (value) => {
                                    if (new Date(value) < new Date()) {
                                        return 'Arrival time must be in the future';
                                    }
                                    return true;
                                },
                                afterDeparture: (value, formValues) => {
                                    if (formValues.departureTime && new Date(value) <= new Date(formValues.departureTime)) {
                                        return 'Arrival time must be after departure time';
                                    }
                                    return true;
                                }
                            }
                        })}
                        className={errors.arrivalTime ? 'error' : ''}
                    />
                    {errors.arrivalTime && (
                        <span className="error-message">{errors.arrivalTime.message}</span>
                    )}
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label>Aircraft Type *</label>
                    <input
                        type="text"
                        placeholder="Airbus A380"
                        {...register('aircraftType', {
                            required: 'Aircraft type is required'
                        })}
                        className={errors.aircraftType ? 'error' : ''}
                    />
                    {errors.aircraftType && (
                        <span className="error-message">{errors.aircraftType.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Status</label>
                    <select
                        {...register('status')}
                    >
                        <option value="">Select Status (Optional)</option>
                        {Object.values(FlightStatus).map(status => (
                            <option key={status} value={status}>{status}</option>
                        ))}
                    </select>
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label>Gate</label>
                    <input
                        type="text"
                        placeholder="A12"
                        {...register('gate', {
                            pattern: {
                                value: /^[A-Z]?\d{1,2}[A-Z]?$/,
                                message: 'Gate format should be like "A12" or "12B"'
                            }
                        })}
                        className={errors.gate ? 'error' : ''}
                    />
                    {errors.gate && (
                        <span className="error-message">{errors.gate.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Terminal</label>
                    <input
                        type="text"
                        placeholder="3"
                        {...register('terminal', {
                            pattern: {
                                value: /^[A-Z]$|^[1-9]$/,
                                message: 'Terminal must be a single letter (A-Z) or a single digit (1-9)'
                            }
                        })}
                        className={errors.terminal ? 'error' : ''}
                    />
                    {errors.terminal && (
                        <span className="error-message">{errors.terminal.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Price *</label>
                    <input
                        type="number"
                        step="0.01"
                        min="0"
                        placeholder="0.00"
                        {...register('price', {
                            required: 'Price is required',
                            min: {
                                value: 0.01,
                                message: 'Price must be positive'
                            },
                            valueAsNumber: true
                        })}
                        className={errors.price ? 'error' : ''}
                    />
                    {errors.price && (
                        <span className="error-message">{errors.price.message}</span>
                    )}
                </div>
            </div>

            <div className="form-actions">
                <button
                    type="submit"
                    disabled={isSubmitting || loading}
                    className={isSubmitting || loading ? 'submitting' : ''}
                >
                    {isSubmitting || loading ? 'Saving...' : (isEditing ? 'Update Flight' : 'Create Flight')}
                </button>
                {onCancel && (
                    <button type="button" onClick={onCancel} disabled={isSubmitting}>
                        Cancel
                    </button>
                )}
            </div>
        </form>
    );
};

export default FlightForm;