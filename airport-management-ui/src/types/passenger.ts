export enum Gender {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    OTHER = 'OTHER',
    UNSPECIFIED = 'UNSPECIFIED'
}

export interface Passenger {
    id: string;
    firstName: string;
    middleName?: string;
    lastName: string;
    gender: Gender;
    dateOfBirth: string;
    email?: string;
    phoneNumber?: string;
    address?: string;
    passportNumber: string;
    passportCountry: string;
    passportExpiryDate: string;
    nationality: string;
    createdAt: string;
    updatedAt: string;
}

export interface PassengerRequest {
    firstName: string;
    middleName?: string;
    lastName: string;
    gender: Gender;
    dateOfBirth: string;
    email?: string;
    phoneNumber?: string;
    address?: string;
    passportNumber: string;
    passportCountry: string;
    passportExpiryDate: string;
    nationality: string;
}

export interface PassengerSearchRequest {
    firstName?: string;
    middleName?: string;
    lastName?: string;
    gender?: Gender;
    address?: string;
    email?: string;
    phoneNumber?: string;
    passportNumber?: string;
    nationality?: string;
}

export interface PassengerResponse {
    id: string;
    firstName: string;
    middleName?: string;
    lastName: string;
    gender: Gender;
    dateOfBirth: string;
    email?: string;
    phoneNumber?: string;
    address?: string;
    passportNumber: string;
    passportCountry: string;
    passportExpiryDate: string;
    nationality: string;
    createdAt: string;
    updatedAt: string;
}