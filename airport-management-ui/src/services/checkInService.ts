import axios from 'axios';
import type {
    FlightCheckInRequest,
    FlightCheckInResponse,
    UpdateSeatRequest
} from '../types/checkIn';


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

export const checkinService = {
    createCheckIn: async (checkInData: FlightCheckInRequest): Promise<FlightCheckInResponse> => {
        const response = await apiClient.post<FlightCheckInResponse>('/checkins', checkInData);
        return response.data;
    },

    getCheckInById: async (id: string): Promise<FlightCheckInResponse> => {
        const response = await apiClient.get<FlightCheckInResponse>(`/checkins/${id}`);
        return response.data;
    },

    getCheckInsByFlight: async (flightNumber: string): Promise<FlightCheckInResponse[]> => {
        const response = await apiClient.get<FlightCheckInResponse[]>(`/checkins/flight/${flightNumber}`);
        return response.data;
    },

    updateSeatNumbers: async (id: string, seatData: UpdateSeatRequest): Promise<FlightCheckInResponse> => {
        const response = await apiClient.patch<FlightCheckInResponse>(`/checkins/${id}/seats`, seatData);
        return response.data;
    },

    cancelCheckIn: async (id: string): Promise<void> => {
        await apiClient.delete(`/checkins/${id}`);
    }
};

export default checkinService;