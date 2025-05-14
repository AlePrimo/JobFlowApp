package com.aleprimo.JobFlowApp.repositories;

import com.aleprimo.JobFlowApp.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICompanyRepository extends JpaRepository<Company,Long> {
}
