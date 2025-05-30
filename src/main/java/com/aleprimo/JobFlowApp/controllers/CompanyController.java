package com.aleprimo.JobFlowApp.controllers;


import com.aleprimo.JobFlowApp.controllers.dtos.CompanyDTO;
import com.aleprimo.JobFlowApp.models.Company;
import com.aleprimo.JobFlowApp.services.ICompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/company")
@Tag(name = "Compañías", description = "Operaciones relacionadas con compañías")
public class CompanyController {

    private final ICompanyService companyService;


    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "Obtener todas las compañías")
    @GetMapping("/findAll")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companies = this.companyService.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(companies);
    }

    @Operation(summary = "Obtener compañía por ID")
    @GetMapping("/findById/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        return this.companyService.findById(id)
                .map(company -> ResponseEntity.ok(mapToDTO(company)))
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "Crear una nueva compañía")
    @PostMapping("/saveCompany")
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyDTO companyDTO) throws URISyntaxException {
        Company company = mapToEntity(companyDTO);
        Company savedCompany = this.companyService.save(company);
        return ResponseEntity.created(new URI("/api/companies/" + savedCompany.getId())).body(mapToDTO(savedCompany));
    }
    @Operation(summary = "Actualizar los datos de una compañía")
    @PutMapping("/updateCompany/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@Valid @PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        Company company = mapToEntity(companyDTO);
        company.setId(id);
        Company updatedCompany = this.companyService.save(company);
        return ResponseEntity.ok(mapToDTO(updatedCompany));
    }

    @Operation(summary = "Eliminar una compañia por su ID")
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        this.companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    private CompanyDTO mapToDTO(Company company) {
        List<Long> jobIds = company.getJobOffers() == null ? List.of() :
                company.getJobOffers().stream().map(job -> job.getId()).collect(Collectors.toList());

        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .jobOfferIds(jobIds)
                .build();
    }

    private Company mapToEntity(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        
        return company;
    }







}
