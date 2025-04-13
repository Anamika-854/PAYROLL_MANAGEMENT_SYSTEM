import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faLock, faEnvelope, faIdCard } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import '../styles/Register.css';

const Register = () => {
    const [formData, setFormData] = useState({
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        employeeCode: '', // Added for employee verification
        role: 'EMPLOYEE' // Default to EMPLOYEE role
    });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState(false);

const handleSubmit = async (e) => {
    console.log("Form data", formData)
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
        setError('Passwords do not match');
        return;
    }
    
    try {
        setLoading(true);
        setError('');
        console.log("Form data in try", formData)
        
        // Update the verification endpoint
        const verifyResponse = await axios.get(
            `/api/v1/employees/verify/${formData.employeeCode}/${encodeURIComponent(formData.email)}`
        );
        console.log("Verify Response", verifyResponse);

        if (!verifyResponse.data.verified) {
            setError('Invalid employee code or email. Please contact your administrator.');
            return;
        }

        // Register the user
        const response = await axios.post('/api/v1/auth/register', {
            username: formData.username,
            email: formData.email,
            password: formData.password,
            employeeCode: formData.employeeCode,
            role: 'ROLE_EMPLOYEE'
        });
        console.log("Register", response)
        
        if (response.data) {
            setSuccess(true);
            setTimeout(() => {
                    window.location.href = '/login';
                }, 2000);
                console.log("Register", response, response.data);
        }
    } catch (error) {
        console.error('Registration error:', error);
        if (error.response?.status === 403) {
            setError('Access denied. Please check your credentials or contact administrator.');
        } else {
            setError(error.response?.data?.message || 'Registration failed. Please try again.');
        }
    } finally {
        setLoading(false);
    }
};

    return (
        <div className="auth-container">
            <div className="auth-box">
                <div className="auth-header">
                    <h1>Register</h1>
                    <p>Create your employee account</p>
                </div>

                {error && (
                    <div className="error-message">{error}</div>
                )}

                {success && (
                    <div className="success-message">
                        Registration successful! Redirecting to login...
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <div className="input-icon">
                            <FontAwesomeIcon icon={faIdCard} />
                        </div>
                        <input
                            type="text"
                            placeholder="Employee Code"
                            value={formData.employeeCode}
                            onChange={(e) => setFormData({...formData, employeeCode: e.target.value})}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <div className="input-icon">
                            <FontAwesomeIcon icon={faUser} />
                        </div>
                        <input
                            type="text"
                            placeholder="Username"
                            value={formData.username}
                            onChange={(e) => setFormData({...formData, username: e.target.value})}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <div className="input-icon">
                            <FontAwesomeIcon icon={faEnvelope} />
                        </div>
                        <input
                            type="email"
                            placeholder="Company Email"
                            value={formData.email}
                            onChange={(e) => setFormData({...formData, email: e.target.value})}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <div className="input-icon">
                            <FontAwesomeIcon icon={faLock} />
                        </div>
                        <input
                            type="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={(e) => setFormData({...formData, password: e.target.value})}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <div className="input-icon">
                            <FontAwesomeIcon icon={faLock} />
                        </div>
                        <input
                            type="password"
                            placeholder="Confirm Password"
                            value={formData.confirmPassword}
                            onChange={(e) => setFormData({...formData, confirmPassword: e.target.value})}
                            required
                        />
                    </div>

                    <button 
                        type="submit" 
                        className="auth-button"
                        disabled={loading}
                    >
                        {loading ? 'Registering...' : 'Register'}
                    </button>
                </form>

                <div className="auth-footer">
                    Already have an account? <Link to="/login">Login</Link>
                </div>
            </div>
        </div>
    );
};

export default Register;