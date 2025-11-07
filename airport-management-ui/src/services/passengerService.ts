import axios from 'axios';
import type {
    PassengerRequest,
    PassengerSearchRequest,
    PassengerResponse
} from '../types/passenger';

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
        throw error;
    }
);

export const passengerService = {
    getPassengers: async (): Promise<PassengerResponse[]> => {
        const response = await apiClient.get<PassengerResponse[]>('/passengers');
        return response.data;
    },

    getPassengerById: async (id: string): Promise<PassengerResponse> => {
        const response = await apiClient.get<PassengerResponse>(`/passengers/${id}`);
        return response.data;
    },

    createPassenger: async (passengerData: PassengerRequest): Promise<PassengerResponse> => {
        const response = await apiClient.post<PassengerResponse>('/passengers', passengerData);
        return response.data;
    },

    updatePassenger: async (id: string, passengerData: PassengerRequest): Promise<PassengerResponse> => {
        const response = await apiClient.put<PassengerResponse>(`/passengers/${id}`, passengerData);
        return response.data;
    },

    deletePassenger: async (id: string): Promise<void> => {
        await apiClient.delete(`/passengers/${id}`);
    },

    searchPassengers: async (searchParams: PassengerSearchRequest): Promise<PassengerResponse[]> => {
        const response = await apiClient.get<PassengerResponse[]>('/passengers/search', {
            params: searchParams
        });
        return response.data;
    }
};

export default passengerService;