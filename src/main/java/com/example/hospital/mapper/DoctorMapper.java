package com.example.hospital.mapper;

import com.example.hospital.dto.response.DoctorResponseDTO;
import com.example.hospital.model.Doctor;

public class DoctorMapper {

    public static DoctorResponseDTO toResponseDTO(Doctor doctor) {
        if (doctor == null) return null;

        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getLicenciaMedica(),
                doctor.getNombre(),
                doctor.getEspecialidad(),
                doctor.getEmail(),
                doctor.getTelefono(),
                doctor.getAñosExperiencia(),
                doctor.getHorarioAtencion(),
                doctor.isDisponible()
        );
    }

}
