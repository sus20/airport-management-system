export interface Flight {
    id: string;
    flightNumber: string;
    airline: string;
    origin: string;
    destination: string;
    departureTime: string;
    arrivalTime: string;
    aircraftType: string;
    status: FlightStatus;
    gate: string;
    terminal: string;
    price: number;
}

export enum FlightStatus {
    SCHEDULED = 'SCHEDULED',
    DELAYED = 'DELAYED',
    DEPARTED = 'DEPARTED',
    ARRIVED = 'ARRIVED',
    CANCELLED = 'CANCELLED'
}

export interface FlightRequest {
    flightNumber: string;
    airline: string;
    origin: string;
    destination: string;
    departureTime: string;
    arrivalTime: string;
    aircraftType: string;
    status?: FlightStatus;
    gate?: string;
    terminal?: string;
    price: number;
}

export interface FlightUpdateGateRequest {
    gate: string;
}

export interface FlightTerminalUpdateRequest {
    terminal: string;
}

export interface FlightStatusUpdateRequest {
    status: FlightStatus;
}

export interface FlightSearchRequest {
    flightNumber?: string;
    airline?: string;
    origin?: string;
    destination?: string;
    aircraftType?: string;
    departureFrom?: string;
    departureTo?: string;
    arrivalFrom?: string;
    arrivalTo?: string;
    priceMin?: number;
    priceMax?: number;
}

export interface PaginationParams {
    page: number;
    size: number;
}