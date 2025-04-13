import axiosInstance from './axiosConfig';

const payrollService = {

    createNewPayroll: async (employeeId) => {
        try {
            // First fetch salary details for the employee
            const salaryResponse = await axiosInstance.get(`/salary-details/employee/${employeeId}`);
            const salaryDetails = salaryResponse.data;

            // Calculate allowances and deductions
            const totalAllowances = (
                salaryDetails.houseRentAllowance +
                salaryDetails.dearnessAllowance +
                salaryDetails.conveyanceAllowance +
                salaryDetails.medicalAllowance +
                salaryDetails.specialAllowance
            );

            const totalDeductions = (
                salaryDetails.providentFund +
                salaryDetails.professionalTax +
                salaryDetails.incomeTax +
                salaryDetails.insurancePremium
            );

            // Create payroll with salary details
            const response = await axiosInstance.post('/payrolls', {
                employeeId: parseInt(employeeId),
                payPeriodStart: new Date().toISOString().split('T')[0],
                payPeriodEnd: new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().split('T')[0],
                status: 'DRAFT',
                basicSalary: salaryDetails.basicSalary,
                allowances: totalAllowances,
                deductions: totalDeductions,
                netSalary: salaryDetails.netSalary
            });

            return response.data;
        } catch (error) {
            if (error.response?.status === 404) {
                throw new Error('Salary details not found for this employee.');
            }
            console.error('Error creating payroll:', error);
            throw error;
        }
    },

    getPayrollByEmployee: async (employeeId) => {
        try {
            const response = await axiosInstance.get(`/payrolls/employee/${employeeId}`);
            return response.data[0]; // Get the latest payroll
        } catch (error) {
            console.error('Error fetching payroll:', error);
            throw error;
        }
    },

    getPayrollHistory: async (employeeId) => {
        try {
            const response = await axiosInstance.get(`/payrolls/employee/${employeeId}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching payroll history:', error);
            throw error;
        }
    },

    processPayroll: async (id) => {
        try {
            const response = await axiosInstance.put(`/payrolls/${id}/process`);
            return response.data;
        } catch (error) {
            console.error('Error processing payroll:', error);
            throw error;
        }
    },

    approvePayroll: async (id) => {
        try {
            const response = await axiosInstance.put(`/payrolls/${id}/approve`);
            return response.data;
        } catch (error) {
            console.error('Error approving payroll:', error);
            throw error;
        }
    },

    downloadPayrollPdf: async (id) => {
        try {
            const response = await axiosInstance.get(`/payrolls/${id}/download`, {
                responseType: 'blob'  // Important for handling binary data
            });
            
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `payroll_${id}.pdf`);
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            
            return true;
        } catch (error) {
            console.error('Error downloading payroll PDF:', error);
            throw error;
        }
    }
};

export default payrollService;