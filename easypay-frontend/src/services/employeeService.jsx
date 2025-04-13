import axiosInstance from './axiosConfig';

const employeeService = {
    getAllEmployees: async () => {
        try {
            const response = await axiosInstance.get('/employees');
            return response.data;
        } catch (error) {
            console.error('Error fetching employees:', error);
            throw error;
        }
    },

    createEmployee: async (employeeData) => {
        try {
            const formData = {
                ...employeeData,
                basicSalary: employeeData.basicSalary ? employeeData.basicSalary.toString() : "0"
            };
    
            console.log('Sending employee data:', formData);
            const response = await axiosInstance.post('/employees', formData);
            return response.data;
        } catch (error) {
            console.error('API Error:', error.response?.data);
            throw error;
        }
    },

    updateEmployee: async (id, employeeData) => {
        try {
            const response = await axiosInstance.put(`/employees/${id}`, employeeData);
            return response.data;
        } catch (error) {
            console.error('Error updating employee:', error);
            throw error;
        }
    },

    deleteEmployee: async (id) => {
        try {
            const response = await axiosInstance.delete(`/employees/${id}`);
            return response.data;
        } catch (error) {
            console.error('Error deleting employee:', error);
            throw error;
        }
    }
};

export default employeeService;