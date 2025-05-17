import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import UserPage from "./pages/UserPage";
import CompanyPage from "./pages/CompanyPage";

function App() {
  return (
    <Router>
      <div style={{ maxWidth: "800px", margin: "0 auto", padding: "20px" }}>
        <h1>JobFlow App (Frontend Básico)</h1>

        <nav style={{ marginBottom: "20px" }}>
          <Link to="/user" style={{ marginRight: "10px" }}>Ingresar como Usuario</Link>
          <Link to="/company">Ingresar como Compañía</Link>
        </nav>

        <Routes>
          <Route path="/user" element={<UserPage />} />
          <Route path="/company" element={<CompanyPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
