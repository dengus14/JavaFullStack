import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";
import "./Login.css";

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [msg, setMsg] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    try {
      const res = await api.post("/login", form);
      // In handleSubmit success block:
      localStorage.setItem("token", res.data);
      setMsg("success|✅ Logged in successfully!");

      // Dispatch custom event
      window.dispatchEvent(new Event('authChange'));

      setTimeout(() => {
        navigate("/dashboard");
      }, 1000);
      
    } catch (err) {
      setMsg("error|❌ Invalid credentials or server error");
    } finally {
      setIsLoading(false);
    }
  };

  const [msgType, msgText] = msg.split("|");

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <div className="vault-icon">🔐</div>
          <h1 className="login-title">File Vault</h1>
          <p className="login-subtitle">Secure file storage and management</p>
        </div>
        
        <form className="login-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Username</label>
            <input 
              className="form-input"
              name="username" 
              placeholder="Enter your username" 
              onChange={handleChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label className="form-label">Password</label>
            <input 
              className="form-input"
              name="password" 
              type="password" 
              placeholder="Enter your password" 
              onChange={handleChange}
              required
            />
          </div>
          
          <button 
            className="login-button" 
            type="submit" 
            disabled={isLoading}
          >
            {isLoading ? "Signing in..." : "Sign In"}
          </button>
        </form>
        
        {msg && (
          <div className={`message ${msgType}`}>
            {msgText}
          </div>
        )}

        <div className="register-hint">
          Don't have an account? 
          <Link to="/" className="register-link">Create one here</Link>
        </div>
      </div>
    </div>
  );
}