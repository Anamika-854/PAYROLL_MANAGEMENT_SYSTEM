import axios from 'axios';
import authService from './authService';

const axiosInstance = axios.create({
    baseURL: '/api/v1'
});

axiosInstance.interceptors.request.use(
    config => {
        // Don't add authentication header for verification endpoints
        if (config.url.includes('/verify')) {
            return config;
        }
        
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        config.headers['Content-Type'] = 'application/json';
        return config;
    },
    error => Promise.reject(error)
);

axiosInstance.interceptors.response.use(
    response => response,
    error => {
        if (error.response) {
            switch (error.response.status) {
                case 401:
                    // Token expired or invalid
                    authService.logout();
                    break;
                case 403:
                    console.error('Access Denied:', error.response.data);
                    break;
                default:
                    console.error('API Error:', error.response.data);
            }
        } else if (error.request) {
            console.error('Network Error:', error.request);
        } else {
            console.error('Error:', error.message);
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;