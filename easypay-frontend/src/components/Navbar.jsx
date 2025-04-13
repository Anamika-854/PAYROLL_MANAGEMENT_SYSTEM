// src/components/Navbar.jsx

import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
    faBell, 
    faUser, 
    faChevronDown, 
    faCog, 
    faSignOutAlt 
} from '@fortawesome/free-solid-svg-icons';
import authService from '../services/authService';
import './styles/Navbar.css';

const Navbar = () => {
    const [showDropdown, setShowDropdown] = useState(false);
    const userRole = localStorage.getItem('role');

    const handleLogout = () => {
        authService.logout();
    };

    return (
        <nav className="navbar">
            <div className="navbar-brand">
                <h1>EasyPay</h1>
            </div>
            
            <div className="navbar-menu">
                
                <div className="profile-menu">
                    <button 
                        className="profile-button"
                        onClick={() => setShowDropdown(!showDropdown)}
                    >
                        <FontAwesomeIcon icon={faUser} />
                        <div className="user-info">
              <p className="user-name">{localStorage.getItem('username') || 'User'}</p>

                
            </div>
                        <FontAwesomeIcon icon={faChevronDown} />
                    </button>
                    
                    {showDropdown && (
                        <div className="dropdown-menu">
                            <button className="dropdown-item" onClick={handleLogout}>
                                <FontAwesomeIcon icon={faSignOutAlt} className="icon" />
                                <span>Logout</span>
                            </button>
                        </div>
                    )}
                </div>
            </div>
        </nav>
    );
};

export default Navbar;