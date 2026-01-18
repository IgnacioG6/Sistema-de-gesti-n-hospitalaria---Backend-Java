package com.example.hospital.mapper;

import com.example.hospital.dto.response.CitaResponseDTO;
import com.example.hospital.model.Cita;

public class CitaMapper {

    public static CitaResponseDTO toResponseDTO(Cita cita) {
        if (cita == null) return null;

        return new CitaResponseDTO(
                cita.getId(),
                cita.getPaciente().getId(),
                cita.getPaciente().getNombre(),
                cita.getDoctor().getId(),
                cita.getDoctor().getNombre(),
                cita.getDoctor().getEspecialidad(),
                cita.getFechaHora(),
                cita.getMotivo(),
                cita.getEstadoCita(),
                cita.getDuracion(),
                cita.getNotas()
        );
    }

}
