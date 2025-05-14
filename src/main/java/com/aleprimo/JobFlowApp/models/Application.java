package com.aleprimo.JobFlowApp.models;


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
    UserEntity user;
    @ManyToOne
    @JoinColumn(name = "job_id")
    Job job;
    @Column(name = "application_date")
    LocalDate applicationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.ENVIADA;

}
