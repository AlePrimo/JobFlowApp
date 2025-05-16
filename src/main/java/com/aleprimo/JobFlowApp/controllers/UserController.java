package com.aleprimo.JobFlowApp.controllers;

import com.aleprimo.JobFlowApp.controllers.dtos.UserEntityDTO;
import com.aleprimo.JobFlowApp.models.Application;
import com.aleprimo.JobFlowApp.models.Role;
import com.aleprimo.JobFlowApp.models.UserEntity;
import com.aleprimo.JobFlowApp.services.IApplicationService;
import com.aleprimo.JobFlowApp.services.IUserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.aleprimo.JobFlowApp.config.AuthUtils.getAuthenticatedEmail;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuarios", description = "Manejo del CRUD de usuarios")
public class UserController {




    private final IUserEntityService userService;
    private final IApplicationService applicationService;

    public UserController(IUserEntityService userService, IApplicationService applicationService) {
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @Operation(summary = "Busqueda de todos los usuarios disponibles")
    @GetMapping
    public ResponseEntity<List<UserEntityDTO>> getAllUsers() {
        List<UserEntityDTO> userEntityDTOS = this.userService.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();

        return ResponseEntity.ok(userEntityDTOS);
    }

    @Operation(summary = "Busqueda de un usuario por su ID")
    @GetMapping("/findById/{id}")
    public ResponseEntity<UserEntityDTO> getUserById(@PathVariable Long id) {
        return this.userService.findById(id)
                .map(user -> ResponseEntity.ok(mapToDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Creacion de un nuevo usuario")
    @PostMapping("/saveUser")
    public ResponseEntity<UserEntityDTO> createUser( @Valid @RequestBody UserEntityDTO userDTO) {
        UserEntity newUser = this.userService.save(mapToEntity(userDTO));
        return ResponseEntity.ok(mapToDTO(newUser));
    }

    @Operation(summary = "Actualizacion  de un usuario")
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserEntityDTO> updateUser(@Valid @PathVariable Long id, @RequestBody UserEntityDTO userDTO) {
        UserEntity user = mapToEntity(userDTO);
        user.setId(id);
        this.userService.save(user);
        return ResponseEntity.ok(mapToDTO(user));
    }

    @Operation(summary = "Eliminacion  de un usuario por su ID")
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
       this.userService.deleteById(id);
    }
    @Operation(summary = "Cargar el CV de un usuario por nombre de archivo")
    @PostMapping("/uploadCV/{userId}")
    public ResponseEntity<String> uploadCV(@PathVariable Long userId,
                                           @RequestParam("file") MultipartFile file) {


        if (file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("El archivo debe ser un PDF.");
        }

        Optional<UserEntity> optionalUser = userService.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserEntity user = optionalUser.get();

        String authEmail = getAuthenticatedEmail();
        if (!user.getEmail().equals(authEmail)) {
            return ResponseEntity.status(403).body("No tiene permiso para subir el CV de otro usuario.");
        }

        try {
            String filename = "cv_user_" + userId + ".pdf";
            Path path = Paths.get("uploads", filename);
            Files.write(path, file.getBytes());

            String downloadUrl = "/api/user/downloadCV/" + filename;
            user.setCvUrl(downloadUrl);
            this.userService.save(user);

            return ResponseEntity.ok("CV subido correctamente. URL: " + downloadUrl);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al guardar el archivo.");
        }
    }


    @Operation(summary = "Descargar un CV (solo el dueño o una compañía puede hacerlo)")
    @GetMapping("/downloadCV/{filename:.+}")
    public ResponseEntity<Resource> downloadCV(@PathVariable String filename) {
        try {
            String authEmail = getAuthenticatedEmail();

            Optional<UserEntity> optionalUser = this.userService.findByEmail(authEmail);
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(403).build();
            }

            UserEntity authenticatedUser = optionalUser.get();

            boolean isOwner = filename.equals("cv_user_" + authenticatedUser.getId() + ".pdf");
            boolean isCompany = authenticatedUser.getRole() == Role.ROLE_COMPANY;

            if (!isOwner && !isCompany) {
                return ResponseEntity.status(403).body(null);
            }

            Path filePath = Paths.get("uploads").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }





    private UserEntityDTO mapToDTO(UserEntity user) {
        List<Long> appIds = user.getApplications() == null ? List.of() :
                user.getApplications().stream().map(Application::getId).toList();

        UserEntityDTO dto = new UserEntityDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setCvUrl(user.getCvUrl());
        dto.setApplicationsIds(appIds);
        dto.setRole(user.getRole());
        return dto;
    }

    private UserEntity mapToEntity(UserEntityDTO dto) {
        UserEntity user = new UserEntity();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setCvUrl(dto.getCvUrl());
        user.setRole(dto.getRole());
        if (dto.getApplicationsIds() != null && !dto.getApplicationsIds().isEmpty()) {
            List<Application> applications = new ArrayList<>();
            for (Long appId : dto.getApplicationsIds()) {
                this.applicationService.findById(appId).ifPresent(applications::add);
            }
            user.setApplications(applications);
        }


        return user;
    }

}
