package com.aleprimo.JobFlowApp.controllers;

import com.aleprimo.JobFlowApp.controllers.dtos.JobDTO;
import com.aleprimo.JobFlowApp.models.Company;
import com.aleprimo.JobFlowApp.models.Job;
import com.aleprimo.JobFlowApp.services.ICompanyService;
import com.aleprimo.JobFlowApp.services.IJobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final IJobService jobService;
    private final ICompanyService companyService;


    public JobController(IJobService jobService, ICompanyService companyService) {
        this.jobService = jobService;
        this.companyService = companyService;
    }


    @GetMapping("/findById/{companyId}/{jobId}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long companyId, @PathVariable Long jobId) {
        Optional<Job> optionalJob = this.jobService.findById(jobId);

        if (optionalJob.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Job job = optionalJob.get();
        if (!job.getCompany().getId().equals(companyId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(mapToDTO(job));
    }

    @GetMapping("/findByCompanyId/{companyId}")
    public ResponseEntity<List<JobDTO>> getJobsByCompany(@PathVariable Long companyId) {
        Optional<Company> optionalCompany = this.companyService.findById(companyId);
        if (optionalCompany.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<JobDTO> jobs = optionalCompany.get().getJobOffers()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(jobs);
    }


    @PostMapping("/saveJob/{companyId}")
    public ResponseEntity<JobDTO> createJob(@Valid @PathVariable Long companyId, @RequestBody JobDTO jobDTO) throws URISyntaxException {
        Optional<Company> optionalCompany = this.companyService.findById(companyId);
        if (optionalCompany.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Job job = mapToEntity(jobDTO);
        job.setCompany(optionalCompany.get());
        Job savedJob = this.jobService.save(job);

        return ResponseEntity.created(new URI("/api/jobs/findById/" + savedJob.getId())).body(mapToDTO(savedJob));
    }



    @PutMapping("/updateJob/{companyId}/{jobId}")
    public ResponseEntity<JobDTO> updateJob(@Valid @PathVariable Long companyId, @PathVariable Long jobId, @RequestBody JobDTO jobDTO) {
        Optional<Company> optionalCompany = this.companyService.findById(companyId);
        Optional<Job> optionalJob = this.jobService.findById(jobId);

        if (optionalCompany.isEmpty() || optionalJob.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Job job = optionalJob.get();
        if (!job.getCompany().getId().equals(companyId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        Job updatedJob = this.jobService.save(job);

        return ResponseEntity.ok(mapToDTO(updatedJob));
    }



    @DeleteMapping("/deleteJob/{companyId}/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long companyId, @PathVariable Long jobId) {
        Optional<Job> optionalJob = this.jobService.findById(jobId);

        if (optionalJob.isEmpty() || !optionalJob.get().getCompany().getId().equals(companyId)) {
            return ResponseEntity.notFound().build();
        }

        this.jobService.deleteById(jobId);
        return ResponseEntity.noContent().build();
    }






    private JobDTO mapToDTO(Job job) {
        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCreationDate(job.getCreationDate());
        dto.setApplications(job.getApplications());
        return dto;
    }

    private Job mapToEntity(JobDTO dto) {
        Job job = new Job();
        job.setId(dto.getId());
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setCreationDate(dto.getCreationDate());
        return job;
    }














}
