package com.example.hospital.dto.request;

import com.example.hospital.model.enums.EstadoFactura;

public record CambiarEstadoFacturaRequest(
        EstadoFactura estado
) {}
