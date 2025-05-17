import React from "react";
import CompanyList from "../components/CompanyList";
import JobList from "../components/JobList";

function CompanyPage() {
  const companyId = 1; // ID de prueba

  return (
    <div>
      <h2>Panel de Compañía</h2>
      <CompanyList />
      <JobList companyId={companyId} />
    </div>
  );
}

export default CompanyPage;