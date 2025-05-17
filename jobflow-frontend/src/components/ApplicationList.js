import React, { useEffect, useState } from "react";

function ApplicationList({ companyId }) {
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    fetch(`http://localhost:8080/api/application/findByCompany/${companyId}`)
      .then((res) => res.json())
      .then((data) => setApplications(data))
      .catch((error) =>
        console.error("Error al obtener aplicaciones de la compañía:", error)
      );
  }, [companyId]);

  const handleStatusUpdate = (applicationId, newStatus) => {
    fetch(
      `http://localhost:8080/api/application/updateStatus/${companyId}/${applicationId}?status=${newStatus}`,
      { method: "PUT" }
    )
      .then(() => {
        setApplications((prev) =>
          prev.map((app) =>
            app.id === applicationId ? { ...app, status: newStatus } : app
          )
        );
      })
      .catch((error) => console.error("Error al actualizar estado:", error));
  };

  return (
    <div>
      <h3>Aplicaciones recibidas</h3>
      <ul>
        {applications.map((app) => (
          <li key={app.id}>
            <p><strong>Usuario:</strong> {app.user.fullName}</p>
            <p><strong>Trabajo:</strong> {app.job.title}</p>
            <p><strong>Estado:</strong> {app.status}</p>
            <button onClick={() => handleStatusUpdate(app.id, "EN_REVISION")}>
              Marcar como EN_REVISION
            </button>
            <button onClick={() => handleStatusUpdate(app.id, "ACEPTADA")}>
              ACEPTAR
            </button>
            <button onClick={() => handleStatusUpdate(app.id, "RECHAZADA")}>
              RECHAZAR
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ApplicationList;
