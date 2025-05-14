package com.aleprimo.JobFlowApp.persistence;

import com.aleprimo.JobFlowApp.models.Application;
import java.util.List;
import java.util.Optional;

public interface IApplicationDAO {

    List<Application> findAll();
    Optional<Application> findById(Long id);
    Application save(Application application);
    void deleteById(Long id);


}
