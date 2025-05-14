package com.aleprimo.JobFlowApp.repositories;

import com.aleprimo.JobFlowApp.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IApplicationRepository extends JpaRepository<Application, Long> {
}
