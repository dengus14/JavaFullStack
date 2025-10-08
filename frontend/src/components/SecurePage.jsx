import React, { useEffect, useState } from "react";
import api from "../api/api";

export default function SecurePage() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    api.get("/secure")
      .then((res) => setMessage(res.data.message))
      .catch(() => setMessage("❌ Access denied"));
  }, []);

  return (
    <div style={{ textAlign: "center" }}>
      <h2>Secure Page</h2>
      <p>{message}</p>
    </div>
  );
}
