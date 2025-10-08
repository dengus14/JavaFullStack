import React, { useState } from "react";
import api from "../api/api";

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [msg, setMsg] = useState("");

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    const res = await api.post("/login", form); // ✅ send JSON body
    localStorage.setItem("token", res.data);
    setMsg("✅ Logged in successfully!");
  } catch (err) {
    setMsg("❌ Invalid credentials or server blocked request");
  }
};


  return (
    <div style={{ maxWidth: 400, margin: "auto" }}>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <input name="username" placeholder="Username" onChange={handleChange} /><br/>
        <input name="password" type="password" placeholder="Password" onChange={handleChange} /><br/>
        <button type="submit">Login</button>
      </form>
      <p>{msg}</p>
    </div>
  );
}
