package com.example.hospital.dto.request;

import com.example.hospital.model.enums.Especialidad;
import com.example.hospital.model.enums.HorarioAtencion;
import jakarta.validation.constraints.*;

public record DoctorRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotBlank(message = "La licencia médica es obligatoria")
        @Pattern(regexp = "^[A-Z0-9-]{5,20}$", message = "La licencia debe ser alfanumérica de entre 5 y 20 caracteres")
        String licenciaMedica,

        @NotNull(message = "La especialidad es obligatoria")
        Especialidad especialidad,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @Size(min = 1, max = 20, message = "El teléfono debe tener entre 1 y 20 caracteres")
        String telefono,

        @Min(value = 0, message = "Los años de experiencia no pueden ser negativos")
        @Max(value = 60, message = "Años de experiencia fuera de rango")
        int añosExperiencia,

        @NotNull(message = "El horario de atención es obligatorio")
        HorarioAtencion horarioAtencion
) {}
