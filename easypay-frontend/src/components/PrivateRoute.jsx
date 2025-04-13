import React from 'react';
import { Navigate } from 'react-router-dom';
import authService from '../services/authService';

const PrivateRoute = ({ component: Component, roles = [] }) => {
    const isAuthenticated = authService.isAuthenticated();
    const userRole = authService.getRole();

    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    if (roles.length && !roles.includes(userRole)) {
        console.log('Access denied. User role:', userRole, 'Required roles:', roles);
        return <Navigate to="/" replace />;
    }

    return <Component />;
};

export default PrivateRoute;