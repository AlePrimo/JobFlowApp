package com.aleprimo.JobFlowApp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder


public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    UserEntity user;
    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonBackReference(value = "job-applications")
    Job job;
    @Column(name = "application_date")
    LocalDate applicationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.ENVIADA;

}
