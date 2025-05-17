import React, { useState } from "react";

function CreateJobForm({ companyId }) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    const jobData = {
      title,
      description,
    };

    fetch(`http://localhost:8080/api/jobs/createJob/${companyId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(jobData),
    })
      .then((response) => {
        if (response.ok) {
          alert("Trabajo creado correctamente");
          setTitle("");
          setDescription("");
        } else {
          alert("Error al crear el trabajo");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Error de conexión");
      });
  };

  return (
    <div>
      <h3>Crear Trabajo</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Título"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
        <br />
        <textarea
          placeholder="Descripción"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />
        <br />
        <button type="submit">Crear</button>
      </form>
    </div>
  );
}

export default CreateJobForm;