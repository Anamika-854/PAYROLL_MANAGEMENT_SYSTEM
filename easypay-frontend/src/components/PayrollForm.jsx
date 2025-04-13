// PayrollForm.jsx
import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import './styles/PayrollForm.css';

const PayrollForm = ({ onSubmit, onClose }) => {
    const [employeeId, setEmployeeId] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!employeeId) {
            setError('Please enter an employee ID');
            return;
        }

        try {
            setLoading(true);
            setError('');
            await onSubmit(employeeId);
            onClose();
        } catch (err) {
            setError(err.response?.data?.message || 'Failed to create payroll');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="payroll-form-overlay">
            <div className="payroll-form-container">
                <div className="payroll-form-header">
                    <h2>Create New Payroll</h2>
                    <button onClick={onClose} className="close-button">
                        <FontAwesomeIcon icon={faTimes} />
                    </button>
                </div>

                {error && (
                    <div className="error-message">
                        {error}
                    </div>
                )}

                <form onSubmit={handleSubmit} className="payroll-form">
                    <div className="form-group">
                        <label>Employee ID</label>
                        <input
                            type="number"
                            value={employeeId}
                            onChange={(e) => setEmployeeId(e.target.value)}
                            min="1"
                            placeholder="Enter employee ID"
                            required
                        />
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
                            className="btn-create"
                        >
                            {loading ? 'Creating...' : 'Create Payroll'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default PayrollForm;