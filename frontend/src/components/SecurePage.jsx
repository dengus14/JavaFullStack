import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./SecurePage.css";

export default function SecurePage() {
  const [message, setMessage] = useState("");
  const [user, setUser] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    api.get("/secure")
      .then((res) => {
        setMessage(res.data.message);
        // Extract username from token or response if available
        setUser("User"); // You can get this from your backend response
      })
      .catch(() => setMessage("❌ Access denied"));
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div className="dashboard-container">
      {/* Header */}
      <header className="dashboard-header">
        <div className="header-left">
          <div className="logo">
            <span className="logo-icon">🔐</span>
            <span className="logo-text">File Vault</span>
          </div>
        </div>
        <div className="header-right">
          <span className="welcome-text">Welcome, {user}!</span>
          <button className="logout-btn" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </header>

      {/* Main Content */}
      <main className="dashboard-main">
        <div className="dashboard-content">
          <div className="status-card">
            <div className="status-icon">✅</div>
            <h2 className="status-title">Dashboard Access</h2>
            <p className="status-message">{message}</p>
          </div>

          {/* File Management Section */}
          <div className="file-section">
            <h3 className="section-title">File Management</h3>
            
            <div className="action-cards">
              <div className="action-card">
                <div className="card-icon">📁</div>
                <h4>My Files</h4>
                <p>Browse and manage your uploaded files</p>
                <button className="card-btn">View Files</button>
              </div>

              <div className="action-card">
                <div className="card-icon">⬆️</div>
                <h4>Upload Files</h4>
                <p>Upload new files to your secure vault</p>
                <button className="card-btn">Upload</button>
              </div>

              <div className="action-card">
                <div className="card-icon">📊</div>
                <h4>Storage Info</h4>
                <p>Check your storage usage and limits</p>
                <button className="card-btn">View Stats</button>
              </div>
            </div>
          </div>

          {/* Quick Stats */}
          <div className="stats-section">
            <div className="stat-item">
              <span className="stat-number">0</span>
              <span className="stat-label">Files Stored</span>
            </div>
            <div className="stat-item">
              <span className="stat-number">0 MB</span>
              <span className="stat-label">Storage Used</span>
            </div>
            <div className="stat-item">
              <span className="stat-number">1 GB</span>
              <span className="stat-label">Total Space</span>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}