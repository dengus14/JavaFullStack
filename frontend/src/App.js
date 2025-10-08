import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Register from "./components/Register";
import Login from "./components/Login";  
import SecurePage from "./components/SecurePage";

function App() {
  // Check if user is authenticated - properly validate the token
  const token = localStorage.getItem("token");
  const isAuthenticated = token && token !== "null" && token !== "undefined" && token.length > 0;

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