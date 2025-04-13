import React, { useState, useEffect } from 'react';
import axiosInstance from '../services/axiosConfig';
import './styles/SalaryDetailsForm.css';

const SalaryDetailsForm = ({ onSubmit, onClose, initialData }) => {
  const [formData, setFormData] = useState({
    employeeId: '',
    basicSalary: '',
    houseRentAllowance: '',
    dearnessAllowance: '',
    conveyanceAllowance: '0', // Initialize with '0'
    medicalAllowance: '0',    // Initialize with '0'
    specialAllowance: '',
    providentFund: '',
    professionalTax: '',
    incomeTax: '',
    insurancePremium: '',
    bankName: '',
    accountNumber: '',
    ifscCode: ''
  });

  const [employees, setEmployees] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchEmployees();
  }, []);

  useEffect(() => {
    if (initialData) {
      const sanitizedData = Object.keys(formData).reduce((acc, key) => {
        // Convert null/undefined values to '0' for numeric fields or '' for others
        if (['conveyanceAllowance', 'medicalAllowance'].includes(key)) {
          acc[key] = initialData[key]?.toString() || '0';
        } else {
          acc[key] = initialData[key]?.toString() || '';
        }
        return acc;
      }, {});
      setFormData(sanitizedData);
    }
  }, [initialData]);

  const fetchEmployees = async () => {
    try {
      const response = await axiosInstance.get('/employees');
      setEmployees(response.data);
    } catch (err) {
      setError('Failed to fetch employees list');
      console.error('Error fetching employees:', err);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.employeeId) {
      setError('Please select an employee');
      return;
    }

    setError('');
    setLoading(true);

    try {
      const processedData = {
        ...formData,
        basicSalary: parseFloat(formData.basicSalary) || 0,
        houseRentAllowance: parseFloat(formData.houseRentAllowance) || 0,
        dearnessAllowance: parseFloat(formData.dearnessAllowance) || 0,
        conveyanceAllowance: parseFloat(formData.conveyanceAllowance) || 0,
        medicalAllowance: parseFloat(formData.medicalAllowance) || 0,
        specialAllowance: parseFloat(formData.specialAllowance) || 0,
        providentFund: parseFloat(formData.providentFund) || 0,
        professionalTax: parseFloat(formData.professionalTax) || 0,
        incomeTax: parseFloat(formData.incomeTax) || 0,
        insurancePremium: parseFloat(formData.insurancePremium) || 0
      };

      // Validate all numeric fields are non-negative
      const numericFields = [
        'basicSalary', 'houseRentAllowance', 'dearnessAllowance', 
        'conveyanceAllowance', 'medicalAllowance', 'specialAllowance',
        'providentFund', 'professionalTax', 'incomeTax', 'insurancePremium'
      ];
      
      const invalidFields = numericFields.filter(field => processedData[field] < 0);
      if (invalidFields.length > 0) {
        setError(`Following fields must be positive: ${invalidFields.join(', ')}`);
        return;
      }

      await onSubmit(processedData);
      onClose();
    } catch (err) {
      console.error('Form submission error:', err);
      const serverErrors = err.response?.data || {};
      const errorMessage = Object.values(serverErrors).join(', ') || 
                          'Failed to save salary details';
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="salary-form-overlay">
      <div className="salary-form-container">
        <h2 className="form-title">Salary Details</h2>
        
        {error && (
          <div className="error-message">{error}</div>
        )}

        <form onSubmit={handleSubmit}>
          {/* Employee Selection Section */}
          <div className="form-section">
            <h3 className="form-section-title">Employee Information</h3>
            <div className="form-group">
              <label>Select Employee</label>
              <select
                value={formData.employeeId}
                onChange={(e) => setFormData({...formData, employeeId: e.target.value})}
                required
                className="form-select"
              >
                <option value="">Select an employee</option>
                {employees.map(emp => (
                  <option key={emp.id} value={emp.id}>
                    {emp.id} - {emp.firstName} {emp.lastName} ({emp.department})
                  </option>
                ))}
              </select>
            </div>
          </div>

          {/* Earnings Section */}
          <div className="form-section">
            <h3 className="form-section-title">Earnings</h3>
            <div className="form-grid">
              <div className="form-group">
                <label>Basic Salary</label>
                <input
                  type="number"
                  value={formData.basicSalary}
                  onChange={(e) => setFormData({...formData, basicSalary: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>

              <div className="form-group">
                <label>House Rent Allowance</label>
                <input
                  type="number"
                  value={formData.houseRentAllowance}
                  onChange={(e) => setFormData({...formData, houseRentAllowance: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>

              <div className="form-group">
                <label>Dearness Allowance</label>
                <input
                  type="number"
                  value={formData.dearnessAllowance}
                  onChange={(e) => setFormData({...formData, dearnessAllowance: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>

              <div className="form-group">
                <label>Conveyance Allowance</label>
                <input
                  type="number"
                  value={formData.conveyanceAllowance}
                  onChange={(e) => setFormData({...formData, conveyanceAllowance: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>

              <div className="form-group">
                <label>Medical Allowance</label>
                <input
                  type="number"
                  value={formData.medicalAllowance}
                  onChange={(e) => setFormData({...formData, medicalAllowance: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>

              <div className="form-group">
                <label>Special Allowance</label>
                <input
                  type="number"
                  value={formData.specialAllowance}
                  onChange={(e) => setFormData({...formData, specialAllowance: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>
            </div>
          </div>

          {/* Deductions Section */}
          <div className="form-section">
            <h3 className="form-section-title">Deductions</h3>
            <div className="form-grid">
              <div className="form-group">
                <label>Provident Fund</label>
                <input
                  type="number"
                  value={formData.providentFund}
                  onChange={(e) => setFormData({...formData, providentFund: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>

              <div className="form-group">
                <label>Professional Tax</label>
                <input
                  type="number"
                  value={formData.professionalTax}
                  onChange={(e) => setFormData({...formData, professionalTax: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>

              <div className="form-group">
                <label>Income Tax</label>
                <input
                  type="number"
                  value={formData.incomeTax}
                  onChange={(e) => setFormData({...formData, incomeTax: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>

              <div className="form-group">
                <label>Insurance Premium</label>
                <input
                  type="number"
                  value={formData.insurancePremium}
                  onChange={(e) => setFormData({...formData, insurancePremium: e.target.value})}
                  required
                  min="0"
                  step="0.01"
                />
              </div>
            </div>
          </div>

          {/* Bank Details Section */}
          <div className="form-section">
            <h3 className="form-section-title">Bank Details</h3>
            <div className="form-grid">
              <div className="form-group">
                <label>Bank Name</label>
                <input
                  type="text"
                  value={formData.bankName}
                  onChange={(e) => setFormData({...formData, bankName: e.target.value})}
                  required
                />
              </div>

              <div className="form-group">
                <label>Account Number</label>
                <input
                  type="text"
                  value={formData.accountNumber}
                  onChange={(e) => setFormData({...formData, accountNumber: e.target.value})}
                  required
                  pattern="[0-9]{9,18}"
                  title="Account number must be between 9 and 18 digits"
                />
              </div>

              <div className="form-group">
                <label>IFSC Code</label>
                <input
                  type="text"
                  value={formData.ifscCode}
                  onChange={(e) => setFormData({...formData, ifscCode: e.target.value})}
                  required
                  pattern="^[A-Z]{4}0[A-Z0-9]{6}$"
                  title="Invalid IFSC code format"
                />
              </div>
            </div>
          </div>

          <div className="form-actions">
            <button
              type="button"
              onClick={onClose}
              className="btn-cancel"
            >
              Cancel
            </button>
            <button
              type="submit"
              disabled={loading}
              className="btn-save"
            >
              {loading ? 'Saving...' : 'Save Details'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SalaryDetailsForm;