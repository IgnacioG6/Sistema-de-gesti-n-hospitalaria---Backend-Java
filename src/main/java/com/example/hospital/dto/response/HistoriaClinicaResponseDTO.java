package com.example.hospital.dto.response;

import com.example.hospital.model.enums.Especialidad;

import java.time.LocalDateTime;

public record HistoriaClinicaResponseDTO(
        Long id,

        Long pacienteId,
        String pacienteNombre,
        String pacienteDni,

        Long doctorId,
        String doctorNombre,
        Especialidad doctorEspecialidad,

        Long citaId,

        LocalDateTime fechaHora,
        String diagnostico,
        String sintomas,
        String tratamientoPrescrito,
        String observaciones
) {
}
