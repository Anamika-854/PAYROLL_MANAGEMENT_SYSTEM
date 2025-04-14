import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faHome,
  faUsers,
  faCalendarAlt,
  faMoneyBillWave,
  faBars,
  faTimes,
  faSignOutAlt,
  faMoneyBill,
  faClipboardList
} from '@fortawesome/free-solid-svg-icons';
import './styles/Sidebar.css';

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [isOpen, setIsOpen] = useState(true);
  const userRole = localStorage.getItem('role');
  const employeeId = localStorage.getItem('employeeId');
  

  const adminMenuItems = [
    { id: 'dashboard', label: 'Dashboard', icon: faHome, path: '/' },
    { id: 'employees', label: 'Employees', icon: faUsers, path: '/employees' },
    { id: 'leave-management', label: 'Leave Management', icon: faClipboardList, path: '/leave-management' },
    { id: 'salary', label: 'Salary Details', icon: faMoneyBill, path: '/salary' },
    { id: 'payroll', label: 'Payroll', icon: faMoneyBillWave, path: '/payroll' }
    
  ];

  const employeeMenuItems = [
    { id: 'dashboard', label: 'Dashboard', icon: faHome, path: '/' },
    { id: 'leave-management', label: 'Leave Management', icon: faClipboardList, path: '/leave-management' },
    { id: 'payroll', label: 'My Payroll', icon: faMoneyBillWave, path: '/payroll' }
    // ,{ id: 'salary', label: 'My Salary', icon: faMoneyBill, path: '/salary' }
  ];

  const menuItems = userRole === 'ADMIN' ? adminMenuItems : employeeMenuItems;

  const handleNavigation = (path) => {
    navigate(path);
  };

  const toggleSidebar = () => {
    setIsOpen(!isOpen);
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <>
      <button className="hamburger-menu" onClick={toggleSidebar}>
        <FontAwesomeIcon icon={isOpen ? faTimes : faBars} />
      </button>

      <div className={`sidebar ${isOpen ? 'open' : ''}`}>
        <div className="sidebar-header">
          <h2>Payroll</h2>
        </div>

        <nav className="sidebar-nav">
          {menuItems.map(item => (
            <button
              key={item.id}
              className={`nav-item ${location.pathname === item.path ? 'active' : ''}`}
              onClick={() => handleNavigation(item.path)}
            >
              <FontAwesomeIcon icon={item.icon} className="nav-icon" />
              <span className="nav-label">{item.label}</span>
            </button>
          ))}

          <button className="nav-item logout-btn" onClick={handleLogout}>
            <FontAwesomeIcon icon={faSignOutAlt} className="nav-icon" />
            <span className="nav-label">Logout</span>
          </button>
        </nav>

        <div className="sidebar-footer">
          <div className="user-profile">
            <div className="avatar">
              {localStorage.getItem('username')?.charAt(0).toUpperCase() || 'U'}
            </div>
            <div className="user-info">
              <p className="user-name">{localStorage.getItem('username') || 'User'}</p>
              <p className="user-role">
                {userRole === 'ADMIN' ? 'Administrator' : 'Employee'}
              </p>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Sidebar;