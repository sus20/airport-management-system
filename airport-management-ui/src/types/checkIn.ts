import type {BaggageRequest, BaggageResponse} from "./baggage.ts";

export enum CheckInStatus {
    CONFIRMED = 'CONFIRMED',
    BOARDED = 'BOARDED',
    CANCELLED = 'CANCELLED'
}

export interface FlightCheckInRequest {
    flightNumber: string;
    passportNumber: string;
    seatNumbers: string[];
    baggages: BaggageRequest[];
}

export interface UpdateSeatRequest {
    seatNumbers: string[];
}

export interface FlightCheckInResponse {
    id: string;
    flightNumber: string;
    seatNumbers: string[];
    checkInTime: string;
    boardingPassUrl: string;
    status: CheckInStatus;
    baggages: BaggageResponse[];
}