import axios from 'axios';

import type {
    Flight,
    FlightRequest,
    FlightUpdateGateRequest,
    FlightTerminalUpdateRequest,
    FlightStatusUpdateRequest,
    FlightSearchRequest,
    PaginationParams
} from '../types/flight';


export interface ValidationError {
    field: string;
    message: string;
}

export interface ApiErrorResponse {
    message: string;
    timestamp: string;
    errors?: ValidationError[];
    status: number;
}

const API_BASE_URL = '/api';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        console.error('API Error:', error.response?.data || error.message);
        new Date().toISOString();
        throw error;
    }
);

export const flightService = {
    getFlights: async (params: PaginationParams): Promise<Flight[]> => {
        const response = await apiClient.get<Flight[]>('/flights', {
            params: {
                page: params.page,
                size: params.size
            }
        });
        return response.data;
    },

    getFlightById: async (id: string): Promise<Flight> => {
        const response = await apiClient.get<Flight>(`/flights/${id}`);
        return response.data;
    },

    createFlight: async (flightData: FlightRequest): Promise<Flight> => {
        const response = await apiClient.post<Flight>('/flights', flightData);
        return response.data;
    },

    updateFlight: async (id: string, flightData: FlightRequest): Promise<Flight> => {
        const response = await apiClient.put<Flight>(`/flights/${id}`, flightData);
        return response.data;
    },

    deleteFlight: async (id: string): Promise<void> => {
        await apiClient.delete(`/flights/${id}`);
    },

    updateGate: async (id: string, gateData: FlightUpdateGateRequest): Promise<Flight> => {
        const response = await apiClient.patch<Flight>(`/flights/${id}/gate`, gateData);
        return response.data;
    },

    updateTerminal: async (id: string, terminalData: FlightTerminalUpdateRequest): Promise<Flight> => {
        const response = await apiClient.patch<Flight>(`/flights/${id}/terminal`, terminalData);
        return response.data;
    },

    updateStatus: async (id: string, statusData: FlightStatusUpdateRequest): Promise<Flight> => {
        const response = await apiClient.patch<Flight>(`/flights/${id}/status`, statusData);
        return response.data;
    },

    searchFlights: async (searchParams: FlightSearchRequest): Promise<Flight[]> => {
        const response = await apiClient.get<Flight[]>('/flights/search', {
            params: searchParams
        });
        return response.data;
    }
};

export default flightService;
