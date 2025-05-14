package com.aleprimo.JobFlowApp.persistence.implementations;

import com.aleprimo.JobFlowApp.models.Company;
import com.aleprimo.JobFlowApp.persistence.ICompanyDAO;
import com.aleprimo.JobFlowApp.repositories.ICompanyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompanyDAOimpl implements ICompanyDAO {


    private final ICompanyRepository companyRepository;

    public CompanyDAOimpl(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    @Override
    public List<Company> findAll() {
        return this.companyRepository.findAll();
    }

    @Override
    public Optional<Company> findById(Long id) {
        return this.companyRepository.findById(id);
    }

    @Override
    public Company save(Company company) {
        return this.companyRepository.save(company);
    }

    @Override
    public void deleteById(Long id) {
      this.companyRepository.deleteById(id);
    }
}
