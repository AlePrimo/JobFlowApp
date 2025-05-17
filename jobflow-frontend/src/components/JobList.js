import React, { useEffect, useState } from "react";

function JobList({ companyId }) {
  const [jobs, setJobs] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8080/api/jobs/findByCompany/${companyId}`)
      .then((res) => res.json())
      .then((data) => setJobs(data))
      .catch((error) => console.error("Error al obtener trabajos:", error));
  }, [companyId]);

  const handleDelete = (id) => {
    fetch(`http://localhost:8080/api/jobs/deleteJob/${companyId}/${id}`, {
      method: "DELETE",
    })
      .then(() => {
        setJobs((prev) => prev.filter((job) => job.id !== id));
      })
      .catch((error) => console.error("Error al eliminar trabajo:", error));
  };

  return (
    <div>
      <h3>Trabajos de la compañía</h3>
      <ul>
        {jobs.map((job) => (
          <li key={job.id}>
            <strong>{job.title}</strong>: {job.description}
            <button onClick={() => handleDelete(job.id)}>Eliminar</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default JobList;