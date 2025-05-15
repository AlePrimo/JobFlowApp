package com.aleprimo.JobFlowApp.controllers.dtos;

import com.aleprimo.JobFlowApp.models.Application;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

public class JobDTO {


    Long id;
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    String title;
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, message = "La descripción debe tener al menos 10 caracteres")
    String description;
    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    LocalDate creationDate = LocalDate.now();
    List<Application> applications = new ArrayList<>();

}
