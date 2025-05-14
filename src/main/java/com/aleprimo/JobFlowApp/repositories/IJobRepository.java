package com.aleprimo.JobFlowApp.repositories;

import com.aleprimo.JobFlowApp.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IJobRepository extends JpaRepository<Job,Long> {
}
