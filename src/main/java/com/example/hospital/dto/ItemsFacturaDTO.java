package com.example.hospital.dto;

import java.math.BigDecimal;

public record ItemsFacturaDTO(
        String descripcion,
        int cantidad,
        BigDecimal precioUnitario,
        BigDecimal total
) {}
