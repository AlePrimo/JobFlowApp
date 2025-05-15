package com.aleprimo.JobFlowApp.controllers;


import com.aleprimo.JobFlowApp.controllers.dtos.ApplicationDTO;
import com.aleprimo.JobFlowApp.models.Application;
import com.aleprimo.JobFlowApp.models.ApplicationStatus;
import com.aleprimo.JobFlowApp.models.Job;
import com.aleprimo.JobFlowApp.models.UserEntity;
import com.aleprimo.JobFlowApp.services.IApplicationService;
import com.aleprimo.JobFlowApp.services.ICompanyService;
import com.aleprimo.JobFlowApp.services.IJobService;
import com.aleprimo.JobFlowApp.services.IUserEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    private final IApplicationService applicationService;
    private final IUserEntityService userService;
    private final IJobService jobService;
    private final ICompanyService companyService;

    public ApplicationController(IApplicationService applicationService, IUserEntityService userService, IJobService jobService, ICompanyService companyService) {
        this.applicationService = applicationService;
        this.userService = userService;
        this.jobService = jobService;
        this.companyService = companyService;
    }



    @PostMapping("/createApplication/{userId}/{jobId}")
    public ResponseEntity<ApplicationDTO> createApplication(@PathVariable Long userId, @PathVariable Long jobId) throws URISyntaxException {
        UserEntity user = this.userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Job job = this.jobService.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Trabajo no encontrado"));

        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        application.setApplicationDate(LocalDate.now());
        application.setStatus(ApplicationStatus.ENVIADA);

        Application saved = this.applicationService.save(application);
        return ResponseEntity.created(new URI("/api/application/findById/" + saved.getId()))
                .body(mapToDTO(saved));
    }




    @PutMapping("/updateStatus/{companyId}/{applicationId}")
    public ResponseEntity<ApplicationDTO> updateStatus(@PathVariable Long companyId,
                                                       @PathVariable Long applicationId,
                                                       @RequestParam ApplicationStatus status) {
        Application application = this.applicationService.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada"));

        if (!application.getJob().getCompany().getId().equals(companyId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        application.setStatus(status);
        Application updated = this.applicationService.save(application);
        return ResponseEntity.ok(mapToDTO(updated));
    }




    @GetMapping("/findById/{companyId}/{applicationId}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long companyId,
                                                             @PathVariable Long applicationId) {
        Application application = this.applicationService.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada"));

        if (!application.getJob().getCompany().getId().equals(companyId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(mapToDTO(application));
    }





    @GetMapping("/findAllByCompany/{companyId}")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsByCompany(@PathVariable Long companyId) {
        List<Application> allApplications = this.applicationService.findAll();
        List<ApplicationDTO> filtered = allApplications.stream()
                .filter(app -> app.getJob().getCompany().getId().equals(companyId))
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filtered);
    }



    @DeleteMapping("/deleteById/{companyId}/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long companyId,
                                                  @PathVariable Long applicationId) {
        Application application = this.applicationService.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Aplicación no encontrada"));

        if (!application.getJob().getCompany().getId().equals(companyId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        this.applicationService.deleteById(applicationId);
        return ResponseEntity.noContent().build();
    }












    private ApplicationDTO mapToDTO(Application application) {
        return ApplicationDTO.builder()
                .id(application.getId())
                .user(application.getUser())
                .job(application.getJob())
                .applicationDate(application.getApplicationDate())
                .status(application.getStatus())
                .build();
    }



}
