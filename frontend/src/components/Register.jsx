import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";
import "./Register.css";

export default function Register() {
  const [form, setForm] = useState({ username: "", password: "", confirmPassword: "" });
  const [msg, setMsg] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (form.password !== form.confirmPassword) {
      setMsg("error|❌ Passwords do not match");
      return;
    }

    setIsLoading(true);
    try {
      
      await api.post("/register", { username: form.username, password: form.password });
      
      
      const loginRes = await api.post("/login", { 
        username: form.username, 
        password: form.password 
      });
      
     // Replace the localStorage.setItem and setTimeout with:
      localStorage.setItem("token", loginRes.data);
      setMsg("success|✅ Registration successful! Redirecting to dashboard...");

      // Dispatch custom event to trigger App.js re-render
      window.dispatchEvent(new Event('authChange'));

      setTimeout(() => {
        navigate("/dashboard");
      }, 500);
      
    } catch (err) {
      if (err.response?.status === 409) {
        setMsg("error|❌ Username already exists. Please choose another.");
      } else {
        setMsg("error|❌ Registration failed. Please try again.");
      }
    } finally {
      setIsLoading(false);
    }
  };

  const [msgType, msgText] = msg.split("|");

  return (
    <div className="register-container">
      <div className="register-card">
        <div className="register-header">
          <div className="vault-icon">🔐</div>
          <h1 className="register-title">Join File Vault</h1>
          <p className="register-subtitle">Create your secure file storage account</p>
        </div>
        
        <form className="register-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Username</label>
            <input 
              className="form-input"
              name="username" 
              placeholder="Choose a username" 
              onChange={handleChange}
              value={form.username}
              required
            />
          </div>
          
          <div className="form-group">
            <label className="form-label">Password</label>
            <input 
              className="form-input"
              name="password" 
              type="password" 
              placeholder="Create a secure password" 
              onChange={handleChange}
              value={form.password}
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label">Confirm Password</label>
            <input 
              className="form-input"
              name="confirmPassword" 
              type="password" 
              placeholder="Confirm your password" 
              onChange={handleChange}
              value={form.confirmPassword}
              required
            />
          </div>
          
          <button 
            className="register-button" 
            type="submit" 
            disabled={isLoading}
          >
            {isLoading ? "Creating Account..." : "Create Account"}
          </button>
        </form>
        
        {msg && (
          <div className={`message ${msgType}`}>
            {msgText}
          </div>
        )}

        <div className="login-hint">
          Already have an account? 
          <Link to="/login" className="login-link">Sign in here</Link>
        </div>
      </div>
    </div>
  );
}