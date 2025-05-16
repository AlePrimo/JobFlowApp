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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Aplicaciones", description = "Manejo de las aplicaciones a empleos")
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


    @Operation(summary = "Postulacion de un usuario a un empleo")
    @PostMapping("/createApplication/{userId}/{jobId}")
    public ResponseEntity<ApplicationDTO> createApplication(@Valid @PathVariable Long userId, @PathVariable Long jobId) throws URISyntaxException {
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



    @Operation(summary = "Actualizacion del estado de una postulacion por parte de la empresa")
    @PutMapping("/updateStatus/{companyId}/{applicationId}")
    public ResponseEntity<ApplicationDTO> updateStatus(@Valid @PathVariable Long companyId,
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



    @Operation(summary = "Busqueda de una postulacion segun el ID de la empresa y el ID de la postulacion")
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




    @Operation(summary = "Busqueda de todas las postulaciones de una empresa segun su ID")
    @GetMapping("/findAllByCompany/{companyId}")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsByCompany(@PathVariable Long companyId) {
        List<Application> allApplications = this.applicationService.findAll();
        List<ApplicationDTO> filtered = allApplications.stream()
                .filter(app -> app.getJob().getCompany().getId().equals(companyId))
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filtered);
    }


    @Operation(summary = "Eliminacion de una postulacion segu ID de compañia y de aplicacion")
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
