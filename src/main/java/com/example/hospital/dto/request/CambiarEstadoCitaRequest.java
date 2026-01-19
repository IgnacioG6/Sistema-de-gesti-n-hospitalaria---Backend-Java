package com.example.hospital.dto.request;

import com.example.hospital.model.enums.EstadoCita;

public record CambiarEstadoCitaRequest(
        EstadoCita estado
) {}
