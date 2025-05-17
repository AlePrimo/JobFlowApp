import React, { useState } from "react";

function CreateCompanyForm() {
  const [name, setName] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    fetch("http://localhost:8080/api/company/saveCompany", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ name }),
    })
      .then((response) => {
        if (response.ok) {
          alert("Compañía creada exitosamente");
          setName("");
        } else {
          alert("Error al crear la compañía");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Error de conexión al crear la compañía");
      });
  };

  return (
    <div>
      <h3>Crear Compañía</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Nombre de la compañía"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <br />
        <button type="submit">Crear</button>
      </form>
    </div>
  );
}

export default CreateCompanyForm;