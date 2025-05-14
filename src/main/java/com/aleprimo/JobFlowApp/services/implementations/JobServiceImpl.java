package com.aleprimo.JobFlowApp.services.implementations;

import com.aleprimo.JobFlowApp.models.Job;
import com.aleprimo.JobFlowApp.persistence.IJobDAO;
import com.aleprimo.JobFlowApp.services.IJobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements IJobService {

    private final IJobDAO jobDAO;

    public JobServiceImpl(IJobDAO jobDAO) {
        this.jobDAO = jobDAO;
    }

    @Override
    public List<Job> findAll() {
        return this.jobDAO.findAll();
    }

    @Override
    public Optional<Job> findById(Long id) {
        return this.jobDAO.findById(id);
    }

    @Override
    public Job save(Job job) {
        return this.jobDAO.save(job);
    }

    @Override
    public void deleteById(Long id) {
this.jobDAO.deleteById(id);
    }
}
