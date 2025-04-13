import React, { useState } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Register from './pages/Register';
import PrivateRoute from './components/PrivateRoute';
import BaseLayout from './components/BaseLayout';
import Dashboard from './pages/Dashboard';
import Employees from './pages/Employees';
import LeaveManagement from './pages/LeaveManagement';
import Payroll from './pages/Payroll';
import SalaryDetails from './pages/SalaryDetails';
import EmployeeSalary from './pages/EmployeeSalary';

function App() {

  const [userRole, setUserRole] = useState('')

  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />

      {/* Private Routes */}
      <Route path="/" element={<BaseLayout />}>
        <Route index element={
          <PrivateRoute 
            component={Dashboard}
            roles={['ADMIN', 'ROLE_EMPLOYEE']} 
          />
        } />
        <Route path="employees" element={
          <PrivateRoute 
            component={Employees} 
            roles={['ADMIN']} 
          />
        } />
        <Route path="/leave-management" element={
          <PrivateRoute 
            component={LeaveManagement} 
            roles={['ADMIN', 'ROLE_EMPLOYEE']} 
          />
        } />
        <Route path="payroll" element={
          <PrivateRoute 
            component={Payroll} 
            roles={['ADMIN', 'ROLE_EMPLOYEE']} 
          />
        } />
        <Route path="/salary" element={
          <PrivateRoute 
            component={SalaryDetails} 
            roles={['ADMIN', 'ROLE_EMPLOYEE']} 
          />
        } />
      </Route>

      {/* Catch all route */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;