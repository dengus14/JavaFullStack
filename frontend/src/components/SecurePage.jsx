import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./SecurePage.css";

export default function SecurePage() {
  const [message, setMessage] = useState("");
  const [user, setUser] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    // Temporary - just set default values
    setUser("User");
    setMessage("Access granted");
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.dispatchEvent(new Event('authChange'));
    navigate("/");
  };

  return (
    <div className="secure-container">
      {/* Top Bar */}
      <div className="top-bar">
        <div className="system-title">
          <span className="security-icon">🔒</span>
          SECURE FILE VAULT
        </div>
        <div className="user-controls">
          <span className="user-info">USER: {user.toUpperCase()}</span>
          <button className="logout-button" onClick={handleLogout}>
            LOGOUT
          </button>
        </div>
      </div>

      {/* Main Content */}
      <div className="main-content">
        <div className="access-panel">
          <div className="panel-header">
            <div className="status-indicator active"></div>
            <h2>SYSTEM ACCESS CONFIRMED</h2>
          </div>
          <div className="access-message">{message.toUpperCase()}</div>
        </div>

        <div className="control-panel">
          <div className="panel-title">FILE OPERATIONS</div>
          <div className="control-grid">
            <button className="control-button primary">
              <div className="button-icon">📂</div>
              <div className="button-text">
                <div className="button-title">VIEW FILES</div>
                <div className="button-desc">Access secure file repository</div>
              </div>
            </button>
          </div>
        </div>

        
      </div>
    </div>
  );
}