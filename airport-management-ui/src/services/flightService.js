import axios from 'axios';

const API_BASE_URL = '/api';

const flightService = {
    getFlights: async (page = 0, size = 5) => {
        try {
            console.log(`Fetching flights from: ${API_BASE_URL}/flights?page=${page}&size=${size}`);
            const response = await axios.get(`${API_BASE_URL}/flights?page=${page}&size=${size}`);
            console.log('Raw API response:', response);
            console.log('Response data:', response.data);
            return response.data;
        } catch (error) {
            console.error('Error fetching flights:', error);
            console.error('Error response:', error.response);
            throw error;
        }
    },

    getFlightById: async (id) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/flights/${id}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching flight:', error);
            throw error;
        }
    }
};

export default flightService;