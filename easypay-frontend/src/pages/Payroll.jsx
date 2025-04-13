import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
    faMoneyBillWave, 
    faSearch,
    faCheck,
    faSpinner,
    faFile,
    faPlus
} from '@fortawesome/free-solid-svg-icons';
import payrollService from '../services/payrollService';
import '../styles/Payroll.css';
import PayrollForm from '../components/PayrollForm';

const Payroll = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [employeeId, setEmployeeId] = useState('');
    const [payrollData, setPayrollData] = useState(null);
    const [payrollHistory, setPayrollHistory] = useState([]);
    const [showCreateForm, setShowCreateForm] = useState(false);
    const userRole = localStorage.getItem('role');
    const currentEmployeeId = localStorage.getItem('employeeId');

    useEffect(() => {
        if (userRole !== 'ADMIN') {
            setEmployeeId(currentEmployeeId);
            fetchPayrollData(currentEmployeeId);
        }
    }, [userRole, currentEmployeeId]);

    const fetchPayrollData = async (id) => {
        try {
            setLoading(true);
            setError(null);
            
            // Fetch both current payroll and history
            const currentPayroll = await payrollService.getPayrollByEmployee(id);
            const history = await payrollService.getPayrollHistory(id);
            
            setPayrollData(currentPayroll);
            setPayrollHistory(history);
        } catch (err) {
            setError('Failed to fetch payroll data');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleProcessPayroll = async (id) => {
        try {
            setLoading(true);
            await payrollService.processPayroll(id);
            await fetchPayrollData(employeeId);
        } catch (err) {
            setError('Failed to process payroll');
        } finally {
            setLoading(false);
        }
    };

    const handleApprovePayroll = async (id) => {
        try {
            setLoading(true);
            await payrollService.approvePayroll(id);
            await fetchPayrollData(employeeId);
        } catch (err) {
            setError('Failed to approve payroll');
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        fetchPayrollData(employeeId);
    };

    const handleCreatePayroll = async (employeeId) => {
        try {
            setLoading(true);
            setError(null);
            const newPayroll = await payrollService.createNewPayroll(employeeId);
            setPayrollData(newPayroll);
            await fetchPayrollData(employeeId); // Refresh all payroll data
            setShowCreateForm(false);
        } catch (err) {
            if (err.message === 'Salary details not found for this employee.') {
                setError('Salary details not found. Please ensure salary details exist for this employee.');
            } else if (err.response?.data) {
                // Handle validation errors from the backend
                const errorMessage = Object.values(err.response.data).join('. ');
                setError(`Failed to create payroll: ${errorMessage}`);
            } else {
                setError('Failed to create payroll. Please try again later.');
            }
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleDownloadPayroll = async (id) => {
        try {
            setLoading(true);
            await payrollService.downloadPayrollPdf(id);
        } catch (err) {
            setError('Failed to download payroll PDF');
            console.error(err);
        } finally {
            setLoading(false);
        }
    };


    return (
        <div className="payroll-page">
            <div className="page-header">
                <h1>{userRole === 'ADMIN' ? 'Payroll Management' : 'My Payroll'}</h1>
                <div className="header-actions">
                    {userRole === 'ADMIN' && (
                        <button 
                            className="btn-primary"
                            onClick={() => setShowCreateForm(true)}
                        >
                            <FontAwesomeIcon icon={faPlus} /> Create New Payroll
                        </button>
                    )}
                </div>
            </div>
    
            {userRole === 'ADMIN' && (
                <div className="search-section">
                    <form onSubmit={handleSubmit} className="search-form">
                        <div className="form-group">
                            <label>Employee ID</label>
                            <input
                                type="number"
                                value={employeeId}
                                onChange={(e) => setEmployeeId(e.target.value)}
                                min="1"
                                required
                            />
                        </div>
                        <button type="submit" className="btn-primary">
                            View Payroll Details
                        </button>
                    </form>
                </div>
            )}
    
            {showCreateForm && (
                <PayrollForm
                    onSubmit={handleCreatePayroll}
                    onClose={() => setShowCreateForm(false)}
                />
            )}
    
            {error && <div className="error-message">{error}</div>}
            {loading && <div className="loading">Loading...</div>}
    
            {payrollData && (
                <div className="payroll-details">
                    <div className="payroll-header">
                        <h2>Current Payroll</h2>
                        {userRole === 'ADMIN' && (
                            <div className="payroll-actions">
                                {payrollData.status === 'DRAFT' && (
                                    <button 
                                        className="btn-process"
                                        onClick={() => handleProcessPayroll(payrollData.id)}
                                    >
                                        <FontAwesomeIcon icon={faSpinner} /> Process
                                    </button>
                                )}
                                {payrollData.status === 'PROCESSED' && (
                                    <button 
                                        className="btn-approve"
                                        onClick={() => handleApprovePayroll(payrollData.id)}
                                    >
                                        <FontAwesomeIcon icon={faCheck} /> Approve
                                    </button>
                                )}
                            </div>
                        )}
                    </div>
                    
                    <div className="payroll-grid">
                        <div className="payroll-card">
                            <h3>Basic Salary</h3>
                            <p>₹{(payrollData.basicSalary || 0).toLocaleString()}</p>
                        </div>
                        <div className="payroll-card">
                            <h3>Allowances</h3>
                            <p>₹{(payrollData.allowances || 0).toLocaleString()}</p>
                        </div>
                        <div className="payroll-card">
                            <h3>Deductions</h3>
                            <p>₹{(payrollData.deductions || 0).toLocaleString()}</p>
                        </div>
                        <div className="payroll-card total">
                            <h3>Net Salary</h3>
                            <p>₹{(payrollData.netSalary || 0).toLocaleString()}</p>
                        </div>
                    </div>
                    
                    <div className="payroll-status">
                        <span className={`status-badge ${payrollData.status?.toLowerCase() || 'draft'}`}>
                            {payrollData.status || 'DRAFT'}
                        </span>
                    </div>
                </div>
            )}
            
            {payrollHistory.length > 0 && (
                <div className="payroll-history">
                    <h2>Payroll History</h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Period</th>
                                <th>Basic Salary</th>
                                <th>Allowances</th>
                                <th>Deductions</th>
                                <th>Net Salary</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {payrollHistory.map((payroll) => (
                                <tr key={payroll.id}>
                                    <td>{`${payroll.payPeriodStart} - ${payroll.payPeriodEnd}`}</td>
                                    <td>₹{(payroll.basicSalary || 0).toLocaleString()}</td>
                                    <td>₹{(payroll.allowances || 0).toLocaleString()}</td>
                                    <td>₹{(payroll.deductions || 0).toLocaleString()}</td>
                                    <td>₹{(payroll.netSalary || 0).toLocaleString()}</td>
                                    <td>
                                        <span className={`status-badge ${payroll.status?.toLowerCase() || 'draft'}`}>
                                            {payroll.status || 'DRAFT'}
                                        </span>
                                    </td>
                                    <td>
                                        <button
                                            className="btn-download"
                                            onClick={() => handleDownloadPayroll(payroll.id)}
                                            disabled={loading}>
                                            <FontAwesomeIcon icon={faFile} /> Download
                                        </button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default Payroll;