package com.example.hospital.dto.response;

import com.example.hospital.dto.ItemsFacturaDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record FacturaResponseDTO(
        Long id,
        String numeroFactura,
        Long pacienteId,
        String pacienteNombre,
        Long citaId,
        List<ItemsFacturaDTO> items,
        BigDecimal subtotal,
        BigDecimal descuento,
        BigDecimal total,
        String estado,
        LocalDate fechaEmision,
        LocalDate fechaVencimiento
) {}
