import React, { useEffect, useState } from "react";
import api from "../services/api";
import JobForm from "./JobForm";

export default function JobList() {
  const [jobs, setJobs] = useState([]);
  const [companyId, setCompanyId] = useState(""); // Para buscar jobs por empresa

  const fetchJobs = () => {
    if (!companyId) return;
    api.get(`/jobs/findAllByCompanyId/${companyId}`)
      .then(res => setJobs(res.data))
      .catch(console.error);
  };

  useEffect(() => {
    fetchJobs();
  }, [companyId]);

  return (
    <div>
      <h2>Trabajos</h2>
      <input
        type="text"
        placeholder="ID de compañía"
        value={companyId}
        onChange={e => setCompanyId(e.target.value)}
      />
      <button onClick={fetchJobs}>Buscar trabajos</button>
      <JobForm companyId={companyId} onJobAdded={fetchJobs} />
      <ul>
        {jobs.map(job => (
          <li key={job.id}>{job.title} - {job.description}</li>
        ))}
      </ul>
    </div>
  );
}
