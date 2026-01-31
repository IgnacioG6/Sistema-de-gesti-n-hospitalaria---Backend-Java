package com.example.hospital.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemsFacturaDTO(
        @NotBlank(message = "La descripción del ítem es obligatoria")
        String descripcion,

        @Min(value = 1, message = "La cantidad mínima es 1")
        int cantidad,

        @NotNull(message = "El precio unitario es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal precioUnitario
) {}
