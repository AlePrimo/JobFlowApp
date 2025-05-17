import React, { useEffect, useState } from "react";

function CreateApplicationForm() {
  const [userId, setUserId] = useState("");
  const [jobId, setJobId] = useState("");
  const [jobs, setJobs] = useState([]);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/user")
      .then((res) => res.json())
      .then((data) => setUsers(data))
      .catch((err) => console.error("Error al obtener usuarios:", err));

    fetch("http://localhost:8080/api/jobs/findAll")
      .then((res) => res.json())
      .then((data) => setJobs(data))
      .catch((err) => console.error("Error al obtener trabajos:", err));
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

    fetch("http://localhost:8080/api/application/createApplication", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userId, jobId }),
    })
      .then((res) => {
        if (res.ok) {
          alert("Aplicación creada exitosamente");
          setUserId("");
          setJobId("");
        } else {
          throw new Error("Error al crear aplicación");
        }
      })
      .catch((err) => alert(err.message));
  };

  return (
    <div>
      <h3>Postularse a un trabajo</h3>
      <form onSubmit={handleSubmit}>
        <label>Usuario:</label>
        <select value={userId} onChange={(e) => setUserId(e.target.value)} required>
          <option value="">Seleccionar usuario</option>
          {users.map((u) => (
            <option key={u.id} value={u.id}>
              {u.fullName}
            </option>
          ))}
        </select>

        <label>Trabajo:</label>
        <select value={jobId} onChange={(e) => setJobId(e.target.value)} required>
          <option value="">Seleccionar trabajo</option>
          {jobs.map((j) => (
            <option key={j.id} value={j.id}>
              {j.title}
            </option>
          ))}
        </select>

        <button type="submit">Aplicar</button>
      </form>
    </div>
  );
}

export default CreateApplicationForm;
