// src/pages/LeaveManagement.jsx

import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
    faPlus, 
    faSearch, 
    faCheck, 
    faTimes,
    faListAlt,
    faClipboardList 
} from '@fortawesome/free-solid-svg-icons';
import axiosInstance from '../services/axiosConfig';
import '../styles/LeaveManagement.css';

const LeaveManagement = () => {
    // Common state for both admin and employee views
    const [activeTab, setActiveTab] = useState('details');
    const [employeeId, setEmployeeId] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [leaveDetails, setLeaveDetails] = useState(null);
    const [leaveRequests, setLeaveRequests] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [adminLeaveRequests, setAdminLeaveRequests] = useState([]);

    // Admin-specific state
    const [searchEmployeeId, setSearchEmployeeId] = useState('');

    // Employee-specific state
    const [newLeaveRequest, setNewLeaveRequest] = useState({
        startDate: '',
        endDate: '',
        leaveType: '',
        reason: ''
    });

    // Get user role and employee ID from localStorage
    const userRole = localStorage.getItem('role');
    // const employeeId = localStorage.getItem('employeeId');

    // Effect to fetch initial data based on role
    useEffect(() => {
        if (userRole === 'ROLE_EMPLOYEE' && employeeId) {
            console.log("Employee leave")
            fetchEmployeeLeaveDetails();
            fetchEmployeeLeaveRequests();
        }
    }, [employeeId, userRole]);

    useEffect(()=> {
        setEmployeeId(localStorage.getItem("employeeId"));
    },[])

    // EMPLOYEE-SPECIFIC API CALLS
    const fetchEmployeeLeaveDetails = async () => {

        try {
            setLoading(true);
            const response = await axiosInstance.get(`/leave-details/employee/${employeeId}`);
            setLeaveDetails(response.data);
            console.log("Response ", response)
            setError('');
        } catch (err) {
            console.error('Error fetching employee leave details:', err);
            setError('Failed to fetch leave details');
        } finally {
            setLoading(false);
        }
    };

    const fetchEmployeeLeaveRequests = async () => {
        try {
            setLoading(true);
            const response = await axiosInstance.get(`/leave-requests/employee/${employeeId}`);
            setLeaveRequests(response.data);
            setError('');
        } catch (err) {
            console.error('Error fetching leave requests:', err);
            setError('Failed to fetch leave requests');
        } finally {
            setLoading(false);
        }
    };

    const handleCreateLeaveRequest = async (e) => {
        e.preventDefault();
        if (!employeeId) {
            setError('Employee ID is required');
            return;
        }
    
        try {
            setLoading(true);
            setError('');
    
            const requestPayload = {
                employeeId: parseInt(employeeId),
                startDate: newLeaveRequest.startDate,
                endDate: newLeaveRequest.endDate,
                leaveType: newLeaveRequest.leaveType,
                reason: newLeaveRequest.reason,
                status: 'PENDING'
            };
    
            // Debug log to verify payload
            console.log('Sending leave request:', requestPayload);
    
            const response = await axiosInstance.post('/leave-requests', requestPayload);
            
            if (response.data) {
                setShowForm(false);
                fetchEmployeeLeaveRequests();
                // Reset form
                setNewLeaveRequest({
                    startDate: '',
                    endDate: '',
                    leaveType: '',
                    reason: ''
                });
                // setEmployeeId(''); // Reset employee ID as well
            }
        } catch (err) {
            console.error('Error creating leave request:', err);
            const errorMessage = err.response?.data?.message || 
                               err.response?.data?.employeeId || 
                               'Failed to create leave request';
            setError(errorMessage);
        } finally {
            setLoading(false);
        }
    };

    // ADMIN-SPECIFIC API CALLS
    const fetchAdminLeaveDetails = async (id) => {
        try {
            setLoading(true);
            const response = await axiosInstance.get(`/leave-details/employee/${id}`);
            setLeaveDetails(response.data);
            setError('');
        } catch (err) {
            setError('No leave details found for this employee.');
            setLeaveDetails(null);
        } finally {
            setLoading(false);
        }
    };

    const fetchAdminLeaveRequests = async () => {
        try {
            setLoading(true);
            const response = await axiosInstance.get('/leave-requests');
            setAdminLeaveRequests(response.data);
            setError('');
        } catch (err) {
            console.error('Error fetching leave requests:', err);
            setError('Failed to fetch leave requests');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (userRole === 'ADMIN' && activeTab === 'requests') {
            fetchAdminLeaveRequests();
        }
    }, [userRole, activeTab]);

    // EMPLOYEE UI COMPONENTS
    const EmployeeLeaveRequestForm = () => (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Create Leave Request</h2>
                <form onSubmit={handleCreateLeaveRequest}>
                    {/* <div className="form-group">
                        <label>Employee ID *</label>
                        <input
                            type="number"
                            required
                            placeholder="Enter your Employee ID"
                            value={employeeId}
                            onChange={(e) => setEmployeeId(e.target.value)}
                        />
                    </div> */}
                    <div className="form-group">
                        <label>Start Date *</label>
                        <input
                            type="date"
                            required
                            value={newLeaveRequest.startDate}
                            onChange={(e) => setNewLeaveRequest({
                                ...newLeaveRequest,
                                startDate: e.target.value
                            })}
                        />
                    </div>
                    <div className="form-group">
                        <label>End Date *</label>
                        <input
                            type="date"
                            required
                            value={newLeaveRequest.endDate}
                            onChange={(e) => setNewLeaveRequest({
                                ...newLeaveRequest,
                                endDate: e.target.value
                            })}
                        />
                    </div>
                    <div className="form-group">
                        <label>Leave Type *</label>
                        <select
                            required
                            value={newLeaveRequest.leaveType}
                            onChange={(e) => setNewLeaveRequest({
                                ...newLeaveRequest,
                                leaveType: e.target.value
                            })}
                        >
                            <option value="">Select Leave Type</option>
                            <option value="CASUAL">Casual Leave</option>
                            <option value="SICK">Sick Leave</option>
                            <option value="PAID">Paid Leave</option>
                            <option value="MARRIAGE">Marriage Leave</option>
                            <option value="MATERNITY">Maternity Leave</option>
                            <option value="PATERNITY">Paternity Leave</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label>Reason *</label>
                        <textarea
                            required
                            placeholder="Enter reason for leave"
                            value={newLeaveRequest.reason}
                            onChange={(e) => setNewLeaveRequest({
                                ...newLeaveRequest,
                                reason: e.target.value
                            })}
                            rows="4"
                            className="form-textarea"
                        />
                    </div>
                    <div className="form-actions">
                        <button type="button" onClick={() => setShowForm(false)}>Cancel</button>
                        <button type="submit" disabled={loading}>
                            {loading ? 'Submitting...' : 'Submit Request'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );

    const renderEmployeeLeaveDetails = () => (
        <div className="leave-details-section">
            {leaveDetails && (
                <div className="leave-details-card">
                    <h3>My Leave Balance</h3>
                    <div className="leave-types-grid">
                        <div className="leave-type">
                            <span>Casual Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.casualLeavesTotal - leaveDetails.casualLeavesUsed}</div>
                                <div>Total: {leaveDetails.casualLeavesTotal}</div>
                                <div>Used: {leaveDetails.casualLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Sick Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.sickLeavesTotal - leaveDetails.sickLeavesUsed}</div>
                                <div>Total: {leaveDetails.sickLeavesTotal}</div>
                                <div>Used: {leaveDetails.sickLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Paid Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.paidLeavesTotal - leaveDetails.paidLeavesUsed}</div>
                                <div>Total: {leaveDetails.paidLeavesTotal}</div>
                                <div>Used: {leaveDetails.paidLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Marriage Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.marriageLeavesTotal - leaveDetails.marriageLeavesUsed}</div>
                                <div>Total: {leaveDetails.marriageLeavesTotal}</div>
                                <div>Used: {leaveDetails.marriageLeavesUsed}</div>
                            </div>
                        </div>
                                    
                        <div className="leave-type">
                            <span>Maternity Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.maternityLeavesTotal - leaveDetails.maternityLeavesUsed}</div>
                                <div>Total: {leaveDetails.maternityLeavesTotal}</div>
                                <div>Used: {leaveDetails.maternityLeavesUsed}</div>
                            </div>
                        </div>
                                    
                        <div className="leave-type">
                            <span>Paternity Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.paternityLeavesTotal - leaveDetails.paternityLeavesUsed}</div>
                                <div>Total: {leaveDetails.paternityLeavesTotal}</div>
                                <div>Used: {leaveDetails.paternityLeavesUsed}</div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );

    const renderEmployeeLeaveRequests = () => (
        <div className="leave-requests-section">
            <div className="section-header">
                <button className="btn-add" onClick={() => setShowForm(true)}>
                    <FontAwesomeIcon icon={faPlus} /> New Leave Request
                </button>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Leave Type</th>
                        <th>From</th>
                        <th>To</th>
                        <th>Status</th>
                        <th>Reason</th>
                    </tr>
                </thead>
                <tbody>
                    {leaveRequests.map(request => (
                        <tr key={request.id}>
                            <td>{request.leaveType}</td>
                            <td>{new Date(request.startDate).toLocaleDateString()}</td>
                            <td>{new Date(request.endDate).toLocaleDateString()}</td>
                            <td>
                                <span className={`status-badge ${request.status.toLowerCase()}`}>
                                    {request.status}
                                </span>
                            </td>
                            <td>{request.reason}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );

    // CONDITIONAL RENDERING BASED ON ROLE
    if (userRole === 'ROLE_EMPLOYEE') {
        return (
            <div className="leave-management-page">
                <h1>Leave Management</h1>

                <div className="tabs">
                    <button 
                        className={`tab ${activeTab === 'details' ? 'active' : ''}`}
                        onClick={() => setActiveTab('details')}
                    >
                        <FontAwesomeIcon icon={faListAlt} /> My Leave Balance
                    </button>
                    <button 
                        className={`tab ${activeTab === 'requests' ? 'active' : ''}`}
                        onClick={() => setActiveTab('requests')}
                    >
                        <FontAwesomeIcon icon={faClipboardList} /> My Leave Requests
                    </button>
                </div>

                {error && <div className="error-message">{error}</div>}
                
                {loading ? (
                    <div className="loading">Loading...</div>
                ) : (
                    <div className="tab-content">
                        {activeTab === 'details' ? renderEmployeeLeaveDetails() : renderEmployeeLeaveRequests()}
                    </div>
                )}

                {showForm && <EmployeeLeaveRequestForm />}
            </div>
        );
    }

    const handleLeaveDetailsSubmit = async (formData) => {
        try {
            setLoading(true);
            await axiosInstance.post('/leave-details', formData);
            setShowForm(false);
            await fetchAdminLeaveDetails(formData.employeeId);
            setError('');
        } catch (err) {
            console.error('Error creating leave details:', err);
            setError('Failed to create leave details');
        } finally {
            setLoading(false);
        }
    };

    const handleAdminLeaveAction = async (requestId, action, approverId) => {
        try {
            setLoading(true);
            console.log("URL", `/leave-requests/${requestId}/${action}`)
            await axiosInstance.put(`/leave-requests/${requestId}/${action}?approverId=1`);
            
            await fetchAdminLeaveRequests(); // Refresh the admin's view
            setError('');
        } catch (err) {
            console.error(`Error ${action}ing leave request:`, err);
            setError(`Failed to ${action} leave request`);
        } finally {
            setLoading(false);
        }
    };

    const LeaveDetailsForm = ({ onSubmit, onClose }) => (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Create Leave Details</h2>
                <form onSubmit={(e) => {
                    e.preventDefault();
                    onSubmit({
                        employeeId: parseInt(employeeId),
                        casualLeavesTotal: 12,
                        sickLeavesTotal: 15,
                        paidLeavesTotal: 15,
                        marriageLeavesTotal: 15,
                        maternityLeavesTotal: 180,
                        paternityLeavesTotal: 15,
                        casualLeavesUsed: 0,
                        sickLeavesUsed: 0,
                        paidLeavesUsed: 0,
                        marriageLeavesUsed: 0,
                        maternityLeavesUsed: 0,
                        paternityLeavesUsed: 0
                    });
                }}>
                    <div className="form-group">
                        <label>Employee ID</label>
                        <input
                            type="number"
                            required
                            value={employeeId}
                            onChange={(e) => setEmployeeId(e.target.value)}
                        />
                    </div>
                    <div className="form-actions">
                        <button type="button" onClick={onClose}>Cancel</button>
                        <button type="submit">Create</button>
                    </div>
                </form>
            </div>
        </div>
    );

    const renderAdminLeaveDetails = () => (
        <div className="leave-details-section">
            <div className="section-header">
                <div className="search-bar">
                    <input
                        type="number"
                        placeholder="Enter Employee ID"
                        value={employeeId}
                        onChange={(e) => setEmployeeId(e.target.value)}
                    />
                    <button onClick={() => fetchAdminLeaveDetails(employeeId)}>
                        <FontAwesomeIcon icon={faSearch} /> Search
                    </button>
                </div>
                <button className="btn-add" onClick={() => setShowForm(true)}>
                    <FontAwesomeIcon icon={faPlus} /> Create Leave Details
                </button>
            </div>

            {leaveDetails && (
                <div className="leave-details-card">
                    <h3>Leave Details for Employee #{leaveDetails.employeeId}</h3>
                    <div className="leave-types-grid">
                        <div className="leave-type">
                            <span>Casual Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.casualLeavesTotal - leaveDetails.casualLeavesUsed}</div>
                                <div>Total: {leaveDetails.casualLeavesTotal}</div>
                                <div>Used: {leaveDetails.casualLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Sick Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.sickLeavesTotal - leaveDetails.sickLeavesUsed}</div>
                                <div>Total: {leaveDetails.sickLeavesTotal}</div>
                                <div>Used: {leaveDetails.sickLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Paid Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.paidLeavesTotal - leaveDetails.paidLeavesUsed}</div>
                                <div>Total: {leaveDetails.paidLeavesTotal}</div>
                                <div>Used: {leaveDetails.paidLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Marriage Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.marriageLeavesTotal - leaveDetails.marriageLeavesUsed}</div>
                                <div>Total: {leaveDetails.marriageLeavesTotal}</div>
                                <div>Used: {leaveDetails.marriageLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Maternity Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.maternityLeavesTotal - leaveDetails.maternityLeavesUsed}</div>
                                <div>Total: {leaveDetails.maternityLeavesTotal}</div>
                                <div>Used: {leaveDetails.maternityLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Paternity Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.paternityLeavesTotal - leaveDetails.paternityLeavesUsed}</div>
                                <div>Total: {leaveDetails.paternityLeavesTotal}</div>
                                <div>Used: {leaveDetails.paternityLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Compensatory Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.compensatoryLeavesEarned - leaveDetails.compensatoryLeavesUsed}</div>
                                <div>Total: {leaveDetails.compensatoryLeavesEarned}</div>
                                <div>Used: {leaveDetails.compensatoryLeavesUsed}</div>
                            </div>
                        </div>

                        <div className="leave-type">
                            <span>Carry Forward Leaves</span>
                            <div className="leave-counts">
                                <div>Available: {leaveDetails.carryForwardLeavesTotal - leaveDetails.carryForwardLeavesUsed}</div>
                                <div>Total: {leaveDetails.carryForwardLeavesTotal}</div>
                                <div>Used: {leaveDetails.carryForwardLeavesUsed}</div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );

    const renderAdminLeaveRequests = () => (
        <div className="leave-requests-section">
            <h3>Leave Requests</h3>
            <table>
                <thead>
                    <tr>
                        <th>Employee ID</th>
                        <th>Leave Type</th>
                        <th>From</th>
                        <th>To</th>
                        <th>Reason</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {adminLeaveRequests.map(request => (
                        <tr key={request.id}>
                            <td>{request.employeeId}</td>
                            <td>{request.leaveType}</td>
                            <td>{new Date(request.startDate).toLocaleDateString()}</td>
                            <td>{new Date(request.endDate).toLocaleDateString()}</td>
                            <td>{request.reason}</td>
                            <td>
                                <span className={`status-badge ${request.status.toLowerCase()}`}>
                                    {request.status}
                                </span>
                            </td>
                            <td>
                                {request.status === 'PENDING' && (
                                    <div className="action-buttons">
                                        <button 
                                            className="btn-approve"
                                            onClick={() => handleAdminLeaveAction(request.id, 'approve')}
                                            title="Approve Leave"
                                        >
                                            <FontAwesomeIcon icon={faCheck} />
                                        </button>
                                        <button 
                                            className="btn-reject"
                                            onClick={() => handleAdminLeaveAction(request.id, 'reject')}
                                            title="Reject Leave"
                                        >
                                            <FontAwesomeIcon icon={faTimes} />
                                        </button>
                                    </div>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {adminLeaveRequests.length === 0 && (
                <div className="no-data">No leave requests found</div>
            )}
        </div>
    );

    return (
        <div className="leave-management-page">
            <h1>Leave Management</h1>

            <div className="tabs">
                <button 
                    className={`tab ${activeTab === 'details' ? 'active' : ''}`}
                    onClick={() => setActiveTab('details')}
                >
                    <FontAwesomeIcon icon={faListAlt} /> 
                    {userRole === 'ROLE_EMPLOYEE' ? 'My Leave Balance' : 'Leave Details'}
                </button>
                <button 
                    className={`tab ${activeTab === 'requests' ? 'active' : ''}`}
                    onClick={() => setActiveTab('requests')}
                >
                    <FontAwesomeIcon icon={faClipboardList} /> 
                    {userRole === 'ROLE_EMPLOYEE' ? 'My Leave Requests' : 'Leave Requests'}
                </button>
            </div>

            {error && <div className="error-message">{error}</div>}
            
            {loading ? (
                <div className="loading">Loading...</div>
            ) : (
                <div className="tab-content">
                    {userRole === 'ROLE_EMPLOYEE' ? (
                        // Employee View
                        activeTab === 'details' ? 
                            renderEmployeeLeaveDetails() : 
                            renderEmployeeLeaveRequests()
                    ) : (
                        // Admin View
                        activeTab === 'details' ? 
                            renderAdminLeaveDetails() : 
                            renderAdminLeaveRequests()
                    )}
                </div>
            )}

            {showForm && (
                userRole === 'ROLE_EMPLOYEE' ? 
                    <EmployeeLeaveRequestForm /> :
                    <LeaveDetailsForm 
                        onSubmit={handleLeaveDetailsSubmit}
                        onClose={() => setShowForm(false)}
                    />
            )}
        </div>
    );
};

export default LeaveManagement;