package com.aleprimo.JobFlowApp.persistence.implementations;

import com.aleprimo.JobFlowApp.models.Job;
import com.aleprimo.JobFlowApp.persistence.IJobDAO;
import com.aleprimo.JobFlowApp.repositories.IJobRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JobDAOimpl implements IJobDAO {

    private final IJobRepository jobRepository;

    public JobDAOimpl(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        return this.jobRepository.findAll();
    }

    @Override
    public Optional<Job> findById(Long id) {
        return this.jobRepository.findById(id);
    }

    @Override
    public Job save(Job job) {
        return this.jobRepository.save(job);
    }

    @Override
    public void deleteById(Long id) {
       this.jobRepository.deleteById(id);
    }
}
