import React, { useState } from "react";
import api from "../api/api";

export default function Register() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [msg, setMsg] = useState("");

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post("/register", form);
      setMsg("✅ Registered successfully! You can now log in.");
    } catch (err) {
      setMsg("❌ " + err.response?.data || "Error registering");
    }
  };

  return (
    <div style={{ maxWidth: 400, margin: "auto" }}>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <input name="username" placeholder="Username" onChange={handleChange} /><br/>
        <input name="password" type="password" placeholder="Password" onChange={handleChange} /><br/>
        <button type="submit">Register</button>
      </form>
      <p>{msg}</p>
    </div>
  );
}
