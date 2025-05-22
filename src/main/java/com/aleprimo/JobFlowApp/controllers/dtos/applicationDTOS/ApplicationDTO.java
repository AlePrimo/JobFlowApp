package com.aleprimo.JobFlowApp.controllers.dtos.applicationDTOS;

import com.aleprimo.JobFlowApp.models.ApplicationStatus;

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
    UserLiteDTO user;

    @NotNull(message = "El trabajo es obligatorio")
    JobLiteDTO job;

    @NotNull(message = "La fecha de postulación es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    LocalDate applicationDate;

    @NotNull(message = "El estado de la aplicación es obligatorio")
    ApplicationStatus status;




}
