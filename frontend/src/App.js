import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Register from "./components/Register";
import Login from "./components/Login";  
import SecurePage from "./components/SecurePage";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  // Check authentication on mount and when localStorage changes
  useEffect(() => {
    const checkAuth = () => {
      const token = localStorage.getItem("token");
      const isAuth = token && token !== "null" && token !== "undefined" && token.length > 0;
      setIsAuthenticated(isAuth);
      setIsLoading(false);
    };

    checkAuth();
    
    // Listen for storage changes (when token is set/removed)
    window.addEventListener('storage', checkAuth);
    
    return () => window.removeEventListener('storage', checkAuth);
  }, []);

  // Custom event for when components update localStorage
  useEffect(() => {
    const handleAuthChange = () => {
      const token = localStorage.getItem("token");
      const isAuth = token && token !== "null" && token !== "undefined" && token.length > 0;
      setIsAuthenticated(isAuth);
    };

    window.addEventListener('authChange', handleAuthChange);
    return () => window.removeEventListener('authChange', handleAuthChange);
  }, []);

  if (isLoading) {
    return <div>Loading...</div>; // Or your loading component
  }

  return (
    <Router>
      <Routes>
        <Route 
          path="/" 
          element={isAuthenticated ? <Navigate to="/dashboard" /> : <Register />} 
        />
        
        <Route 
          path="/login" 
          element={isAuthenticated ? <Navigate to="/dashboard" /> : <Login />} 
        />
        
        <Route 
          path="/dashboard" 
          element={isAuthenticated ? <SecurePage /> : <Navigate to="/" />} 
        />
        
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </Router>
  );
}

export default App;