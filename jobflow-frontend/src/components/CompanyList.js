import React, { useEffect, useState } from "react";
import api from "../services/api";

export default function CompanyList() {
  const [companies, setCompanies] = useState([]);

  useEffect(() => {
    api.get("/company/findAll")
      .then(res => setCompanies(res.data))
      .catch(console.error);
  }, []);

  return (
    <div>
      <h2>Compañías</h2>
      <ul>
        {companies.map(company => (
          <li key={company.id}>{company.name}</li>
        ))}
      </ul>
    </div>
  );
}
