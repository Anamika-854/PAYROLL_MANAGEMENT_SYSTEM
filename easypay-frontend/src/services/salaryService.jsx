// src/services/salaryService.js
import axiosInstance from './axiosConfig';

const salaryService = {
    getSalaryDetailsByEmployeeId: async (employeeId) => {
        try {
            const response = await axiosInstance.get(`/salary-details/employee/${employeeId}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching salary details:', error);
            throw error;
        }
    },

    createSalaryDetails: async (salaryData) => {
        try {
            const response = await axiosInstance.post('/salary-details', salaryData);
            return response.data;
        } catch (error) {
            console.error('Error creating salary details:', error);
            throw error;
        }
    },

    updateSalaryDetails: async (id, salaryData) => {
        try {
            const response = await axiosInstance.put(`/salary-details/${id}`, salaryData);
            return response.data;
        } catch (error) {
            console.error('Error updating salary details:', error);
            throw error;
        }
    },

    calculateSalary: async (employeeId) => {
        try {
            const response = await axiosInstance.post(`/salary-details/calculate/${employeeId}`);
            return response.data;
        } catch (error) {
            console.error('Error calculating salary:', error);
            throw error;
        }
    }
};

export default salaryService;