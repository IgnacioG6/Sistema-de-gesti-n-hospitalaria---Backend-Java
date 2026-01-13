package com.example.hospital.dto.response;

import com.example.hospital.model.enums.Especialidad;
import com.example.hospital.model.enums.EstadoCita;

import java.time.LocalDateTime;

public record CitaResponseDTO(
        Long id,
        Long pacienteId,
        String pacienteNombre,
        Long doctorId,
        String doctorNombre,
        Especialidad doctorEspecialidad,
        LocalDateTime fechaHora,
        String motivo,
        EstadoCita estado,
        int duracion,
        String notas
) {}
