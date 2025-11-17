import axios from 'axios';
import type {BaggageRequest, BaggageResponse, BaggageStatusUpdateRequest} from '../types/baggage';


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

        throw {
            message: error.response?.data?.message || error.message || 'An unexpected error occurred',
            timestamp: new Date().toISOString(),
            errors: error.response?.data?.errors,
            status: error.response?.status || 500
        };
    }
);

export const baggageService = {
    getBaggageById: async (id: string): Promise<BaggageResponse> => {
        console.log('Fetching baggage with ID:', id);
        const response = await apiClient.get<BaggageResponse>(`/baggage/${id}`);
        console.log('Response:', response.data);
        return response.data;
    },

    updateBaggageWeight: async (id: string, weightData: BaggageRequest): Promise<BaggageResponse> => {
        const response = await apiClient.patch<BaggageResponse>(`/baggage/${id}/weight`, weightData);
        return response.data;
    },

    updateBaggageStatus: async (id: string, statusData: BaggageStatusUpdateRequest): Promise<BaggageResponse> => {
        const response = await apiClient.patch<BaggageResponse>(`/baggage/${id}/status`, statusData);
        return response.data;
    }
};

export default baggageService;