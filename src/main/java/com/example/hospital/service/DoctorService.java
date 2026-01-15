package com.example.hospital.service;

import com.example.hospital.dto.response.DoctorResponseDTO;
import com.example.hospital.exception.EntityNotFoundException;
import com.example.hospital.mapper.DoctorMapper;
import com.example.hospital.model.Doctor;
import com.example.hospital.model.Paciente;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    private final List<Doctor> doctores = new ArrayList<>();

    public List<DoctorResponseDTO> obtenerTodos() {
        return doctores.stream()
                .map(DoctorMapper::toResponseDTO)
                .toList();
    }


    public DoctorResponseDTO buscarDoctorPorId(Long id) {
        for (Doctor doctor : doctores) {
            if (doctor.getId().equals(id)) {
                return DoctorMapper.toResponseDTO(doctor);
            }
        }
        throw new EntityNotFoundException("Doctor no encontrado con id: " + id);
    }

    public DoctorResponseDTO buscarDoctorPorLicencia(String licencia) {
        for (Doctor doctor : doctores) {
            if (doctor.getLicenciaMedica().equals(licencia)) {
                return DoctorMapper.toResponseDTO(doctor);
            }
        }
        throw new EntityNotFoundException("Doctor no encontrado con licencia: " + licencia);
    }


}
