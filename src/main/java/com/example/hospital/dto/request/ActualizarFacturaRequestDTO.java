package com.example.hospital.dto.request;

import com.example.hospital.dto.ItemsFacturaDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ActualizarFacturaRequestDTO(
        @NotNull(message = "El descuento es obligatorio (puede ser 0)")
        @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
        BigDecimal descuento,

        @NotEmpty(message = "La factura debe tener al menos un ítem")
        @Valid
        List<ItemsFacturaDTO> items
) {}