package com.example.hospital.dto.request;

import com.example.hospital.model.enums.EstadoFactura;
import jakarta.validation.constraints.NotNull;

public record CambiarEstadoFacturaRequest(
        @NotNull(message = "El estado es obligatorio")
        EstadoFactura estado
) {}
