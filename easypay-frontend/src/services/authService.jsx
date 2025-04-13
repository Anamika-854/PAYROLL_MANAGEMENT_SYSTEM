import axios from 'axios';

const authService = {
    decodeToken: (token) => {
        try {
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            return JSON.parse(window.atob(base64));
        } catch (error) {
            console.error('Error decoding token:', error);
            return null;
        }
    },

    login: async (username, password) => {
        try {
            const response = await axios.post('/api/v1/auth/login', {
                username,
                password
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            
            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('username', username);
                localStorage.setItem('role', response.data.role);

                if (response.data.employeeId) {
                    localStorage.setItem('employeeId', response.data.employeeId);
                }
                
                // Set role based on username
                // if (username.toLowerCase() === 'admin') {
                //     localStorage.setItem('role', 'ADMIN');
                // } else {
                //     localStorage.setItem('role', 'ROLE_EMPLOYEE');
                // }
            }
            return response.data;
        } catch (error) {
            console.error('Login error:', error);
            throw error;
        }
    },

    logout: () => {
        localStorage.clear();
        window.location.href = '/login';
    },

    getToken: () => {
        return localStorage.getItem('token');
    },

    isAuthenticated: () => {
        return !!localStorage.getItem('token');
    },

    getRole: () => {
        return localStorage.getItem('role');
    }
};

export default authService;