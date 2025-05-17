import React, { useState } from "react";
import api from "../services/api";

export default function UploadCV({ userId }) {
  const [file, setFile] = useState(null);

  const handleUpload = () => {
    if (!file) {
      alert("Seleccioná un archivo primero");
      return;
    }
    const formData = new FormData();
    formData.append("file", file);

    api.post(`/user/uploadCV/${userId}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    })
      .then(res => alert(res.data))
      .catch(err => {
        console.error(err);
        alert("Error al subir CV");
      });
  };

  return (
    <div>
      <h3>Subir CV</h3>
      <input type="file" onChange={e => setFile(e.target.files[0])} />
      <button onClick={handleUpload}>Subir</button>
    </div>
  );
}
