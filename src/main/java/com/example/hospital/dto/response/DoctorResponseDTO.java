package com.example.hospital.dto.response;

import com.example.hospital.model.enums.Especialidad;
import com.example.hospital.model.enums.HorarioAtencion;

public record DoctorResponseDTO(
        Long id,
        String nombre,
        String licenciaMedica,
        Especialidad especialidad,
        String email,
        String telefono,
        int añosExperiencia,
        HorarioAtencion horarioAtencion,
        boolean disponible
) {
}
