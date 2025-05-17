import React from "react";
import CompanyList from "../components/CompanyList";
import JobList from "../components/JobList";
import CreateCompanyForm from "../components/CreateCompanyForm";
import CreateJobForm from "../components/CreateJobForm";
import ApplicationList from "../components/ApplicationList";

function CompanyPage() {
  const companyId = 1; // ID de prueba

  return (
    <div>
      <h2>Panel de Compañía</h2>
      <CreateCompanyForm />
      <CreateJobForm companyId={companyId} />
      <JobList companyId={companyId} />
      <ApplicationList companyId={companyId} />
    </div>
  );
}

export default CompanyPage;