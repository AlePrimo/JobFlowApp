package com.aleprimo.JobFlowApp.services.implementations;

import com.aleprimo.JobFlowApp.models.Company;
import com.aleprimo.JobFlowApp.persistence.ICompanyDAO;
import com.aleprimo.JobFlowApp.services.ICompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final ICompanyDAO companyDAO;

    public CompanyServiceImpl(ICompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    public List<Company> findAll() {
        return this.companyDAO.findAll();
    }

    @Override
    public Optional<Company> findById(Long id) {
        return this.companyDAO.findById(id);
    }

    @Override
    public Company save(Company company) {
        return this.companyDAO.save(company);
    }

    @Override
    public void deleteById(Long id) {
this.companyDAO.deleteById(id);
    }
}
