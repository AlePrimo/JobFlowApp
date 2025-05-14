package com.aleprimo.JobFlowApp.services.implementations;

import com.aleprimo.JobFlowApp.models.Application;
import com.aleprimo.JobFlowApp.persistence.IApplicationDAO;
import com.aleprimo.JobFlowApp.services.IApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationServiceImpl implements IApplicationService {

    private final IApplicationDAO applicationDAO;

    public ApplicationServiceImpl(IApplicationDAO applicationDAO) {
        this.applicationDAO = applicationDAO;
    }

    @Override
    public List<Application> findAll() {
        return this.applicationDAO.findAll();
    }

    @Override
    public Optional<Application> findById(Long id) {
        return this.applicationDAO.findById(id);
    }

    @Override
    public Application save(Application application) {
        return this.applicationDAO.save(application);
    }

    @Override
    public void deleteById(Long id) {
this.applicationDAO.deleteById(id);
    }
}
