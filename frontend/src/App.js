import React from "react";
import Register from "./components/Register";
import Login from "./components/Login";  
import SecurePage from "./components/SecurePage";

function App() {
  return (
    <div>
      <Register />
      <hr />
      <Login />
      <hr />
      <SecurePage />
    </div>
  );
}

export default App;