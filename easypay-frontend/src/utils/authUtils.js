export const hasRole = (requiredRole) => {
    const token = localStorage.getItem('token');
    if (!token) return false;
    
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const payload = JSON.parse(window.atob(base64));
        
        // Update role check to match new role names
        const userRole = payload.authorities?.[0]?.replace('ROLE_', '');
        return userRole === requiredRole;
    } catch (error) {
        console.error('Error checking role:', error);
        return false;
    }
};