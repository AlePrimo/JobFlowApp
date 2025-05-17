import React from "react";
import UserList from "../components/UserList";
import UploadCV from "../components/UploadCV";

function UserPage() {
  const userId = 1; // ID de prueba

  return (
    <div>
      <h2>Panel de Usuario</h2>
      <UserList />
      <UploadCV userId={userId} />
    </div>
  );
}

export default UserPage;