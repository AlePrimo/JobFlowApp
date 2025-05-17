import React, { useState } from "react";

function CreateUserForm() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch("http://localhost:8080/api/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(formData),
    })
      .then((response) => {
        if (response.ok) {
          alert("Usuario creado exitosamente");
          setFormData({ name: "", email: "", password: "" });
        } else {
          alert("Error al crear el usuario");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Error al crear el usuario");
      });
  };

  return (
    <div>
      <h3>Crear Usuario</h3>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="name"
          placeholder="Nombre"
          value={formData.name}
          onChange={handleChange}
          required
        />
        <br />
        <input
          type="email"
          name="email"
          placeholder="Correo electrónico"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <br />
        <input
          type="password"
          name="password"
          placeholder="Contraseña"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <br />
        <button type="submit">Crear Usuario</button>
      </form>
    </div>
  );
}

export default CreateUserForm;