package com.example.hospital.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActualizarHistoriaClinicaRequestDTO(
        @NotBlank(message = "El diagnóstico es obligatorio")
        @Size(min = 10, max = 2000, message = "El diagnóstico debe tener entre 10 y 2000 caracteres")
        String diagnostico,

        @NotBlank(message = "Los síntomas son obligatorios")
        @Size(min = 5, max = 1000, message = "Los síntomas deben tener entre 5 y 1000 caracteres")
        String sintomas,

        @NotBlank(message = "El tratamiento prescrito es obligatorio")
        @Size(min = 10, max = 2000, message = "El tratamiento debe tener entre 10 y 2000 caracteres")
        String tratamientoPrescrito,

        @Size(min = 1, max = 1000, message = "Las observaciones deben tener entre 1 y 1000 caracteres")
        String observaciones
) {}