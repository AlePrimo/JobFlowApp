import React from "react";
import UserList from "../components/UserList";
import UploadCV from "../components/UploadCV";
import CreateUserForm from "../components/CreateUserForm";

function UserPage() {
  const userId = 1; // ID de prueba

  return (
    <div>
      <h2>Panel de Usuario</h2>
      <CreateUserForm />
      <UploadCV userId={userId} />
      <UserList />
    </div>
  );
}

export default UserPage;
