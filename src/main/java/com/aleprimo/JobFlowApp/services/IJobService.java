package com.aleprimo.JobFlowApp.services;

import com.aleprimo.JobFlowApp.models.Job;

import java.util.List;
import java.util.Optional;

public interface IJobService {
    List<Job> findAll();
    Optional<Job> findById(Long id);
    Job save(Job job);
    void deleteById(Long id);

}
