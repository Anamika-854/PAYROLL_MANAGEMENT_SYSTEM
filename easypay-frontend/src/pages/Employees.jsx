import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus, faEdit, faTrash, faSearch } from '@fortawesome/free-solid-svg-icons';
import EmployeeForm from '../components/EmployeeForm';
import employeeService from '../services/employeeService';
import '../styles/Employees.css';
import { Navigate } from 'react-router-dom';

const Employees = () => {
    const [employees, setEmployees] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [selectedEmployee, setSelectedEmployee] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const userRole = localStorage.getItem('role');

    useEffect(() => {
        fetchEmployees();
    }, []);
    
    const fetchEmployees = async () => {
        try {
            setLoading(true);
            const data = await employeeService.getAllEmployees();
            setEmployees(data);
            setError(null);
        } catch (err) {
            console.error('Fetch error:', err);
            setError('Failed to load employees');
        } finally {
            setLoading(false);
        }
    };

    // Redirect if not admin
    if (userRole !== 'ADMIN') {
        return <Navigate to="/" />;
    }

    const handleSubmit = async (formData) => {
        try {
            if (selectedEmployee) {
                await employeeService.updateEmployee(selectedEmployee.id, formData);
            } else {
                await employeeService.createEmployee(formData);
            }
            await fetchEmployees();
            setShowForm(false);
        } catch (err) {
            alert('Failed to save employee');
            throw err;
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this employee?')) {
            try {
                await employeeService.deleteEmployee(id);
                await fetchEmployees();
            } catch (error) {
                alert('Failed to delete employee');
            }
        }
    };

    const handleEdit = (employee) => {
        setSelectedEmployee(employee);
        setShowForm(true);
    };

    const handleAddNew = () => {
        setSelectedEmployee(null);
        setShowForm(true);
    };

    if (loading) return <div>Loading...</div>;

    return (
        <div className="employees-page">
            <div className="page-header">
                <h1>Employee Management</h1>
                <button className="btn-add" onClick={handleAddNew}>
                    <FontAwesomeIcon icon={faPlus} /> Add Employee
                </button>
            </div>

            {error && <div className="error-message">{error}</div>}

            <div className="search-box">
                <FontAwesomeIcon icon={faSearch} />
                <input
                    type="text"
                    placeholder="Search employees..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </div>

            <div className="employees-table">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Department</th>
                            <th>Designation</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {employees.filter(employee =>
                            Object.values(employee)
                                .join(' ')
                                .toLowerCase()
                                .includes(searchTerm.toLowerCase())
                        ).map(employee => (
                            <tr key={employee.id}>
                                <td>{employee.id}</td>
                                <td>{`${employee.firstName} ${employee.lastName}`}</td>
                                <td>{employee.email}</td>
                                <td>{employee.department}</td>
                                <td>{employee.designation}</td>
                                <td>
                                    <span className={`status ${employee.active ? 'active' : 'inactive'}`}>
                                        {employee.active ? 'Active' : 'Inactive'}
                                    </span>
                                </td>
                                <td className="actions">
                                    <button className="btn-edit" onClick={() => handleEdit(employee)}>
                                        <FontAwesomeIcon icon={faEdit} />
                                    </button>
                                    <button className="btn-delete" onClick={() => handleDelete(employee.id)}>
                                        <FontAwesomeIcon icon={faTrash} />
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            {showForm && (
                <EmployeeForm
                    employee={selectedEmployee}
                    onSubmit={handleSubmit}
                    onClose={() => setShowForm(false)}
                />
            )}
        </div>
    );
};

export default Employees;