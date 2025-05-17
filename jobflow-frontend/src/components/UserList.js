import React, { useEffect, useState } from "react";
import api from "../services/api";

export default function UserList() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    api.get("/user")
      .then(res => setUsers(res.data))
      .catch(console.error);
  }, []);

  return (
    <div>
      <h2>Usuarios</h2>
      <ul>
        {users.map(user => (
          <li key={user.id}>{user.fullName} - {user.email}</li>
        ))}
      </ul>
    </div>
  );
}
