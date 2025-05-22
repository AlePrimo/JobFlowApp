package com.aleprimo.JobFlowApp.controllers.dtos.applicationDTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobLiteDTO {
    Long id;

    @NotBlank(message = "El título del trabajo es obligatorio")
    @Size(min = 3, max = 100)
    String title;

    @NotBlank(message = "La descripción es obligatoria")
    String description;

    @NotNull(message = "La fecha de creación es obligatoria")
    LocalDate creationDate;

    @NotNull(message = "La compañía es obligatoria")
    CompanyLiteDTO company;
}
