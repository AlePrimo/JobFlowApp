package com.aleprimo.JobFlowApp.controllers.dtos;

import com.aleprimo.JobFlowApp.models.ApplicationStatus;
import com.aleprimo.JobFlowApp.models.Job;
import com.aleprimo.JobFlowApp.models.UserEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationDTO {


    Long id;
    @NotNull(message = "El usuario es obligatorio")
    UserEntity user;
    @NotNull(message = "El trabajo es obligatorio")
    Job job;
    @NotNull(message = "La fecha de postulación es obligatoria")
    @PastOrPresent(message = "La fecha de postulación no puede ser futura")
    LocalDate applicationDate = LocalDate.now();

    @NotNull(message = "El estado de la aplicación es obligatorio")
    private ApplicationStatus status = ApplicationStatus.ENVIADA;



}
