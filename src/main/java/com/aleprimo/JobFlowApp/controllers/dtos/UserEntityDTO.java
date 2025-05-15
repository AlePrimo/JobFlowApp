package com.aleprimo.JobFlowApp.controllers.dtos;

import com.aleprimo.JobFlowApp.models.Application;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserEntityDTO {

    Long id;
    @NotBlank(message = "El nombre completo no puede estar vacío")
    @Size(min = 3, max = 30, message = "El nombre debe tener entre 3 y 100 caracteres")
    String fullName;
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    String email;
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    LocalDate birthDate;
    @URL(message = "La URL del CV debe ser válida")
    String cvUrl;
    List<Application> applications = new ArrayList<>();


}
