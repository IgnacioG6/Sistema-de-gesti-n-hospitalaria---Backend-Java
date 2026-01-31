package com.example.hospital.mapper;

import com.example.hospital.dto.request.DoctorRequestDTO;
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

    public static Doctor toEntity(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setNombre(dto.nombre());
        doctor.setLicenciaMedica(dto.licenciaMedica());
        doctor.setEspecialidad(dto.especialidad());
        doctor.setEmail(dto.email());
        doctor.setTelefono(dto.telefono());
        doctor.setAñosExperiencia(dto.añosExperiencia());
        doctor.setHorarioAtencion(dto.horarioAtencion());
        doctor.setDisponible(true);
        return doctor;
    }

    public static void updateEntity(Doctor doctor, DoctorRequestDTO dto) {
        doctor.setNombre(dto.nombre());
        doctor.setLicenciaMedica(dto.licenciaMedica());
        doctor.setEspecialidad(dto.especialidad());
        doctor.setEmail(dto.email());
        doctor.setTelefono(dto.telefono());
        doctor.setAñosExperiencia(dto.añosExperiencia());
        doctor.setHorarioAtencion(dto.horarioAtencion());
    }
}
