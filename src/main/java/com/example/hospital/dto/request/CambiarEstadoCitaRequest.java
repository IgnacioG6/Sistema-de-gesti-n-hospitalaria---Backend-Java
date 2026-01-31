package com.example.hospital.dto.request;

import com.example.hospital.model.enums.EstadoCita;
import jakarta.validation.constraints.NotNull;

public record CambiarEstadoCitaRequest(
        @NotNull(message = "El estado es obligatorio")
        EstadoCita estado
) {}
