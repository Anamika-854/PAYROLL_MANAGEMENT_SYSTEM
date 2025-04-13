import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus, faEdit, faSearch } from '@fortawesome/free-solid-svg-icons';
import SalaryDetailsForm from '../components/SalaryDetailsForm';
import salaryService from '../services/salaryService';
import '../styles/SalaryDetails.css';

const SalaryDetails = () => {
    const [employeeId, setEmployeeId] = useState('');
    const [salaryDetails, setSalaryDetails] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const userRole = localStorage.getItem('role');

    const fetchSalaryDetails = async (id) => {
        try {
            setLoading(true);
            const data = await salaryService.getSalaryDetailsByEmployeeId(id);
            setSalaryDetails(data);
            setError('');
        } catch (err) {
            console.error('Error fetching salary details:', err);
            setError('No salary details found for this employee.');
            setSalaryDetails(null);
        } finally {
            setLoading(false);
        }
    };

    const handleSearch = (e) => {
        e.preventDefault();
        if (employeeId) {
            fetchSalaryDetails(employeeId);
        }
    };

    const handleSubmit = async (formData) => {
        try {
            if (salaryDetails?.id) {
                await salaryService.updateSalaryDetails(salaryDetails.id, formData);
            } else {
                await salaryService.createSalaryDetails(formData);
            }
            await fetchSalaryDetails(formData.employeeId);
            setShowForm(false);
            // Update the search field with the selected employee ID
            setEmployeeId(formData.employeeId);
        } catch (error) {
            throw error;
        }
    };

    return (
        <div className="salary-details-page">
            <div className="page-header">
                <h1 className="page-title">Salary Management</h1>
                {userRole === 'ADMIN' && (
                    <button 
                        onClick={() => setShowForm(true)}
                        className="btn-add"
                    >
                        <FontAwesomeIcon icon={faPlus} /> Add New Salary Details
                    </button>
                )}
            </div>

            {userRole === 'ADMIN' && !showForm && (
                <div className="search-section">
                    <form onSubmit={handleSearch} className="search-form">
                        <input
                            type="number"
                            placeholder="Enter Employee ID"
                            value={employeeId}
                            onChange={(e) => setEmployeeId(e.target.value)}
                            className="search-input"
                        />
                        <button type="submit" className="btn-search">
                            <FontAwesomeIcon icon={faSearch} /> Search
                        </button>
                    </form>
                </div>
            )}

            {error && (
                <div className="error-message">
                    {error}
                </div>
            )}

            {showForm && (
                <SalaryDetailsForm
                    onSubmit={handleSubmit}
                    onClose={() => setShowForm(false)}
                    initialData={salaryDetails}
                />
            )}

            {salaryDetails && !showForm && (
                <div className="salary-details-card">
                    <div className="card-header">
                        <h2>Salary Details</h2>
                        {userRole === 'ADMIN' && (
                            <button
                                onClick={() => setShowForm(true)}
                                className="btn-edit"
                            >
                                <FontAwesomeIcon icon={faEdit} /> Edit Details
                            </button>
                        )}
                    </div>

                    <div className="details-grid">
                        <div className="details-section">
                            <h3>Earnings</h3>
                            <div className="details-list">
                                <p>Basic Salary: ₹{salaryDetails.basicSalary?.toLocaleString()}</p>
                                <p>HRA: ₹{salaryDetails.houseRentAllowance?.toLocaleString()}</p>
                                <p>DA: ₹{salaryDetails.dearnessAllowance?.toLocaleString()}</p>
                                <p>Special Allowance: ₹{salaryDetails.specialAllowance?.toLocaleString()}</p>
                            </div>
                        </div>

                        <div className="details-section">
                            <h3>Deductions</h3>
                            <div className="details-list">
                                <p>PF: ₹{salaryDetails.providentFund?.toLocaleString()}</p>
                                <p>Professional Tax: ₹{salaryDetails.professionalTax?.toLocaleString()}</p>
                                <p>Income Tax: ₹{salaryDetails.incomeTax?.toLocaleString()}</p>
                                <p>Insurance Premium: ₹{salaryDetails.insurancePremium?.toLocaleString()}</p>
                            </div>
                        </div>
                    </div>

                    <div className="bank-details">
                        <h3>Bank Details</h3>
                        <div className="bank-details-grid">
                            <div className="detail-item">
                                <label>Bank Name</label>
                                <p>{salaryDetails.bankName}</p>
                            </div>
                            <div className="detail-item">
                                <label>Account Number</label>
                                <p>{salaryDetails.accountNumber}</p>
                            </div>
                            <div className="detail-item">
                                <label>IFSC Code</label>
                                <p>{salaryDetails.ifscCode}</p>
                            </div>
                        </div>
                    </div>

                    <div className="salary-summary">
                        <div className="summary-item">
                            <label>Gross Salary</label>
                            <p>₹{salaryDetails.grossSalary?.toLocaleString()}</p>
                        </div>
                        <div className="summary-item">
                            <label>Total Deductions</label>
                            <p>₹{salaryDetails.totalDeductions?.toLocaleString()}</p>
                        </div>
                        <div className="summary-item total">
                            <label>Net Salary</label>
                            <p>₹{salaryDetails.netSalary?.toLocaleString()}</p>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default SalaryDetails;