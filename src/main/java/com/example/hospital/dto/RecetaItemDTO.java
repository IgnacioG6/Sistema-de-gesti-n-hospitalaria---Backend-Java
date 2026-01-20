package com.example.hospital.dto;

public record RecetaItemDTO(
        String medicamento,
        String dosis,
        String frecuencia,
        int duracionDias,
        String instrucciones
) {}
