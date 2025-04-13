// src/pages/EmployeeSalary.jsx

import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMoneyBill } from '@fortawesome/free-solid-svg-icons';
import axiosInstance from '../services/axiosConfig';
import '../styles/EmployeeSalary.css';

const EmployeeSalary = () => {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [salaryDetails, setSalaryDetails] = useState(null);
    const employeeId = localStorage.getItem('employeeId');

    useEffect(() => {
        fetchSalaryDetails();
    }, []);

    const fetchSalaryDetails = async () => {
        try {
            setLoading(true);
            const response = await axiosInstance.get(`/salary-details/employee/${employeeId}`);
            console.log('Salary Details:', response.data); // Debug log
            setSalaryDetails(response.data);
        } catch (err) {
            console.error('Error fetching salary details:', err);
            setError('Failed to fetch salary details. Please try again later.');
        } finally {
            setLoading(false);
        }
    };

    if (loading) return <div className="loading">Loading...</div>;
    if (error) return <div className="error-message">{error}</div>;

    return (
        <div className="employee-salary-page">
            <div className="page-header">
                <h1>My Salary Details</h1>
            </div>

            {salaryDetails ? (
                <div className="salary-container">
                    {/* Earnings Section */}
                    <div className="salary-section">
                        <h2>
                            <FontAwesomeIcon icon={faMoneyBill} className="section-icon" />
                            Earnings
                        </h2>
                        <div className="details-grid">
                            <div className="salary-card">
                                <label>Basic Salary</label>
                                <p className="amount">₹{salaryDetails.basicSalary?.toLocaleString()}</p>
                            </div>
                            <div className="salary-card">
                                <label>House Rent Allowance</label>
                                <p className="amount">₹{salaryDetails.houseRentAllowance?.toLocaleString()}</p>
                            </div>
                            <div className="salary-card">
                                <label>Dearness Allowance</label>
                                <p className="amount">₹{salaryDetails.dearnessAllowance?.toLocaleString()}</p>
                            </div>
                            <div className="salary-card">
                                <label>Conveyance Allowance</label>
                                <p className="amount">₹{salaryDetails.conveyanceAllowance?.toLocaleString()}</p>
                            </div>
                            <div className="salary-card">
                                <label>Medical Allowance</label>
                                <p className="amount">₹{salaryDetails.medicalAllowance?.toLocaleString()}</p>
                            </div>
                            <div className="salary-card">
                                <label>Special Allowance</label>
                                <p className="amount">₹{salaryDetails.specialAllowance?.toLocaleString()}</p>
                            </div>
                        </div>
                    </div>

                    {/* Deductions Section */}
                    <div className="salary-section">
                        <h2>
                            <FontAwesomeIcon icon={faMoneyBill} className="section-icon" />
                            Deductions
                        </h2>
                        <div className="details-grid">
                            <div className="salary-card deduction">
                                <label>Provident Fund</label>
                                <p className="amount">₹{salaryDetails.providentFund?.toLocaleString()}</p>
                            </div>
                            <div className="salary-card deduction">
                                <label>Professional Tax</label>
                                <p className="amount">₹{salaryDetails.professionalTax?.toLocaleString()}</p>
                            </div>
                            <div className="salary-card deduction">
                                <label>Income Tax</label>
                                <p className="amount">₹{salaryDetails.incomeTax?.toLocaleString()}</p>
                            </div>
                            <div className="salary-card deduction">
                                <label>Insurance Premium</label>
                                <p className="amount">₹{salaryDetails.insurancePremium?.toLocaleString()}</p>
                            </div>
                        </div>
                    </div>

                    {/* Bank Details Section */}
                    <div className="salary-section">
                        <h2>
                            <FontAwesomeIcon icon={faMoneyBill} className="section-icon" />
                            Bank Details
                        </h2>
                        <div className="bank-details">
                            <div className="salary-card">
                                <label>Bank Name</label>
                                <p>{salaryDetails.bankName}</p>
                            </div>
                            <div className="salary-card">
                                <label>Account Number</label>
                                <p>{salaryDetails.accountNumber}</p>
                            </div>
                            <div className="salary-card">
                                <label>IFSC Code</label>
                                <p>{salaryDetails.ifscCode}</p>
                            </div>
                        </div>
                    </div>

                    {/* Summary Section */}
                    <div className="salary-summary">
                        <div className="summary-card">
                            <label>Gross Salary</label>
                            <p className="amount">₹{salaryDetails.grossSalary?.toLocaleString()}</p>
                        </div>
                        <div className="summary-card deduction">
                            <label>Total Deductions</label>
                            <p className="amount">₹{salaryDetails.totalDeductions?.toLocaleString()}</p>
                        </div>
                        <div className="summary-card net">
                            <label>Net Salary</label>
                            <p className="amount">₹{salaryDetails.netSalary?.toLocaleString()}</p>
                        </div>
                    </div>
                </div>
            ) : (
                <div className="no-data">
                    <p>No salary details found. Please contact HR if this is unexpected.</p>
                </div>
            )}
        </div>
    );
};

export default EmployeeSalary;