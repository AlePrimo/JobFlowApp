package com.aleprimo.JobFlowApp.services;

import com.aleprimo.JobFlowApp.models.Application;

import java.util.List;
import java.util.Optional;

public interface IApplicationService {

    List<Application> findAll();
    Optional<Application> findById(Long id);
    Application save(Application application);
    void deleteById(Long id);

}
