package com.example.hospital.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecetaItemDTO(
        @NotBlank(message = "El nombre del medicamento es obligatorio")
        String medicamento,

        @NotBlank(message = "La dosis es obligatoria (ej: 500mg)")
        String dosis,

        @NotBlank(message = "La frecuencia es obligatoria (ej: cada 8 horas)")
        String frecuencia,

        @Min(value = 1, message = "La duración debe ser al menos 1 día")
        int duracionDias,

        @Size(min = 1, max = 500, message = "Las instrucciones deben tener entre 1 y 500 caracteres")
        String instrucciones
) {}
