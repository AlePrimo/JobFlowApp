import React, { useState } from "react";
import api from "../services/api";

export default function JobForm({ companyId, onJobAdded }) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!companyId) {
      alert("Ingresá ID de compañía antes");
      return;
    }
    api.post("/jobs/saveJob", { title, description, creationDate: new Date(), companyId })
      .then(() => {
        setTitle("");
        setDescription("");
        onJobAdded();
      })
      .catch(err => {
        console.error(err);
        alert("Error al crear trabajo");
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>Crear Trabajo</h3>
      <input
        type="text"
        placeholder="Título"
        value={title}
        onChange={e => setTitle(e.target.value)}
        required
      />
      <textarea
        placeholder="Descripción"
        value={description}
        onChange={e => setDescription(e.target.value)}
        required
      />
      <button type="submit">Crear</button>
    </form>
  );
}
