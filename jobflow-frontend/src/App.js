import React from "react";
import UserList from "./components/UserList";
import CompanyList from "./components/CompanyList";
import JobList from "./components/JobList";
import UploadCV from "./components/UploadCV";

function App() {

  const userId = 1;

  return (
    <div style={{ maxWidth: "900px", margin: "0 auto", padding: "20px" }}>
      <h1>JobFlow App (Frontend Básico)</h1>
      <UserList />
      <CompanyList />
      <JobList />
      <UploadCV userId={userId} />
    </div>
  );
}

export default App;

