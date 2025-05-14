package com.aleprimo.JobFlowApp.services;

import com.aleprimo.JobFlowApp.models.Company;

import java.util.List;
import java.util.Optional;

public interface ICompanyService {

    List<Company> findAll();
    Optional<Company> findById(Long id);
    Company save(Company company);
    void deleteById(Long id);



}
