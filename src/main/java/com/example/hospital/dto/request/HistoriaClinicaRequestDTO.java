package com.example.hospital.dto.request;

import java.time.LocalDateTime;

public record HistoriaClinicaRequestDTO(
        Long idCita,
        String diagnostico,
        String sintomas,
        String tratamientoPrescrito,
        String observaciones

        ) {}
