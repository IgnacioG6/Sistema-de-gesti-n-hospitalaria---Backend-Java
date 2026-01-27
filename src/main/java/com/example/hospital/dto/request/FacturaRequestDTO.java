package com.example.hospital.dto.request;

import com.example.hospital.dto.ItemsFacturaDTO;

import java.math.BigDecimal;
import java.util.List;

public record FacturaRequestDTO(
        Long citaId,
        List<ItemsFacturaDTO> items,
        BigDecimal descuento
) {}
