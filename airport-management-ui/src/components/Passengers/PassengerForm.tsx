import React, {useEffect} from 'react';
import {useForm} from 'react-hook-form';
import {type PassengerRequest, Gender} from "../../types/passenger.ts";
import type {ApiErrorResponse, ValidationError} from "../../services/passengerService.ts";
import './PassengerForm.css';

interface PassengerFormProps {
    onSubmit: (data: PassengerRequest) => Promise<void>;
    onCancel?: () => void;
    initialData?: Partial<PassengerRequest>;
    isEditing?: boolean;
    serverError?: ApiErrorResponse | null;
    loading?: boolean;
}

const PassengerForm: React.FC<PassengerFormProps> = ({
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
    } = useForm<PassengerRequest>({
        defaultValues: initialData
    });

    useEffect(() => {
        if (serverError?.errors) {
            clearErrors();
            serverError.errors.forEach((validationError: ValidationError) => {
                setError(validationError.field as any, {
                    type: 'server',
                    message: validationError.message
                });
            });
        }
    }, [serverError, setError, clearErrors]);

    const today = new Date().toISOString().split('T')[0];

    const handleFormSubmit = async (data: PassengerRequest) => {
        try {
            clearErrors();
            const transformedData: PassengerRequest = {
                ...data,
                middleName: data.middleName?.trim() || undefined,
                phoneNumber: data.phoneNumber?.trim() || undefined,
                email: data.email?.trim() || undefined,
                address: data.address?.trim() || undefined,
            };
            await onSubmit(transformedData);
        } catch (error) {
            console.error('Form submission error:', error);
        }
    };

    const hasGeneralServerError = serverError && (!serverError.errors || serverError.errors.length === 0);

    return (
        <form onSubmit={handleSubmit(handleFormSubmit)} className="passenger-form">
            <h2>{isEditing ? 'Edit Passenger' : 'Create New Passenger'}</h2>

            {hasGeneralServerError && (
                <div className="server-error general-error">
                    <strong>Error:</strong> {serverError.message}
                </div>
            )}

            <div className="form-row">
                <div className="form-group">
                    <label>First Name *</label>
                    <input
                        type="text"
                        placeholder="John"
                        {...register('firstName', {
                            required: 'First name is required'
                        })}
                        className={errors.firstName ? 'error' : ''}
                    />
                    {errors.firstName && (
                        <span className="error-message">{errors.firstName.message}</span>
                    )}
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
                    <label>Last Name *</label>
                    <input
                        type="text"
                        placeholder="Doe"
                        {...register('lastName', {
                            required: 'Last name is required'
                        })}
                        className={errors.lastName ? 'error' : ''}
                    />
                    {errors.lastName && (
                        <span className="error-message">{errors.lastName.message}</span>
                    )}
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label>Gender *</label>
                    <select
                        {...register('gender', {
                            required: 'Gender is required'
                        })}
                        className={errors.gender ? 'error' : ''}
                    >
                        <option value="">Select Gender</option>
                        <option value={Gender.MALE}>Male</option>
                        <option value={Gender.FEMALE}>Female</option>
                        <option value={Gender.OTHER}>Other</option>
                    </select>
                    {errors.gender && (
                        <span className="error-message">{errors.gender.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Date of Birth *</label>
                    <input
                        type="date"
                        max={today}
                        {...register('dateOfBirth', {
                            required: 'Date of birth is required',
                            validate: {
                                pastDate: (value) => {
                                    if (new Date(value) >= new Date()) {
                                        return 'Date of birth must be in the past';
                                    }
                                    return true;
                                }
                            }
                        })}
                        className={errors.dateOfBirth ? 'error' : ''}
                    />
                    {errors.dateOfBirth && (
                        <span className="error-message">{errors.dateOfBirth.message}</span>
                    )}
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label>Email</label>
                    <input
                        type="email"
                        placeholder="john.doe@example.com"
                        {...register('email', {
                            required: false,
                            pattern: {
                                value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                                message: 'Invalid email address'
                            }
                        })}
                        className={errors.email ? 'error' : ''}
                    />
                    {errors.email && (
                        <span className="error-message">{errors.email.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Phone Number</label>
                    <input
                        type="tel"
                        placeholder="+1234567890"
                        {...register('phoneNumber', {
                            required: false,
                            pattern: {
                                value: /^$|^[0-9+\-]{7,15}$/,
                                message: 'Invalid phone number format'
                            },
                        })}
                        className={errors.phoneNumber ? 'error' : ''}
                    />
                    {errors.phoneNumber && (
                        <span className="error-message">{errors.phoneNumber.message}</span>
                    )}
                </div>
            </div>

            <div className="form-group">
                <label>Address</label>
                <textarea
                    rows={3}
                    placeholder="123 Main St, City, Country"
                    {...register('address')}
                />
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label>Passport Number *</label>
                    <input
                        type="text"
                        placeholder="AB123456"
                        {...register('passportNumber', {
                            required: 'Passport number is required',
                            pattern: {
                                value: /^[A-Za-z0-9]{6,9}$/,
                                message: 'Passport must be 6-9 characters long and contain only letters and numbers'
                            }
                        })}
                        className={errors.passportNumber ? 'error' : ''}
                    />
                    {errors.passportNumber && (
                        <span className="error-message">{errors.passportNumber.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Passport Country *</label>
                    <input
                        type="text"
                        placeholder="United States"
                        {...register('passportCountry', {
                            required: 'Passport country is required'
                        })}
                        className={errors.passportCountry ? 'error' : ''}
                    />
                    {errors.passportCountry && (
                        <span className="error-message">{errors.passportCountry.message}</span>
                    )}
                </div>
            </div>

            <div className="form-row">
                <div className="form-group">
                    <label>Passport Expiry Date *</label>
                    <input
                        type="date"
                        min={today}
                        {...register('passportExpiryDate', {
                            required: 'Passport expiry date is required',
                            validate: {
                                futureDate: (value) => {
                                    if (new Date(value) <= new Date()) {
                                        return 'Passport expiry must be in the future';
                                    }
                                    return true;
                                }
                            }
                        })}
                        className={errors.passportExpiryDate ? 'error' : ''}
                    />
                    {errors.passportExpiryDate && (
                        <span className="error-message">{errors.passportExpiryDate.message}</span>
                    )}
                </div>

                <div className="form-group">
                    <label>Nationality *</label>
                    <input
                        type="text"
                        placeholder="American"
                        {...register('nationality', {
                            required: 'Nationality is required'
                        })}
                        className={errors.nationality ? 'error' : ''}
                    />
                    {errors.nationality && (
                        <span className="error-message">{errors.nationality.message}</span>
                    )}
                </div>
            </div>

            <div className="form-actions">
                <button
                    type="submit"
                    disabled={isSubmitting || loading}
                    className={isSubmitting || loading ? 'submitting' : ''}
                >
                    {isSubmitting || loading ? 'Saving...' : (isEditing ? 'Update Passenger' : 'Create Passenger')}
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

export default PassengerForm;