package com.aleprimo.JobFlowApp.persistence.implementations;

import com.aleprimo.JobFlowApp.models.Application;
import com.aleprimo.JobFlowApp.persistence.IApplicationDAO;
import com.aleprimo.JobFlowApp.repositories.IApplicationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationDAOimpl implements IApplicationDAO {

    private final IApplicationRepository applicationRepository;

    public ApplicationDAOimpl(IApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> findAll() {
        return this.applicationRepository.findAll();
    }

    @Override
    public Optional<Application> findById(Long id) {
        return this.applicationRepository.findById(id);
    }

    @Override
    public Application save(Application application) {
        return this.applicationRepository.save(application);
    }

    @Override
    public void deleteById(Long id) {
this.applicationRepository.deleteById(id);
    }
}
