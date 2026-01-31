package com.example.hospital.service;

import com.example.hospital.dto.request.DoctorRequestDTO;
import com.example.hospital.dto.response.DoctorResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.mapper.DoctorMapper;
import com.example.hospital.model.Doctor;
import com.example.hospital.model.enums.Especialidad;
import com.example.hospital.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorResponseDTO> obtenerTodos() {
        return doctorRepository.findAll().stream().map(DoctorMapper::toResponseDTO).toList();
    }

    public Doctor buscarEntidadPorId(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Doctor no encontrado con id: " + id));
    }

    public DoctorResponseDTO buscarDoctorPorId(Long id) {
        Doctor doctor = buscarEntidadPorId(id);
        return DoctorMapper.toResponseDTO(doctor);
    }

    public DoctorResponseDTO buscarDoctorPorLicencia(String licencia) {
        return doctorRepository.findByLicenciaMedica(licencia)
                .map(DoctorMapper::toResponseDTO)
                .orElseThrow(() -> new EntidadNoEncontradaException("Doctor no encontrado con Licencia: " + licencia));
    }

    public DoctorResponseDTO registarDoctor(DoctorRequestDTO dto) {
        Doctor doctor = DoctorMapper.toEntity(dto);
        doctorRepository.save(doctor);
        return DoctorMapper.toResponseDTO(doctor);
    }


    public DoctorResponseDTO actualizarDoctor(Long id, DoctorRequestDTO dto) {
        Doctor doctor = buscarEntidadPorId(id);
        DoctorMapper.updateEntity(doctor, dto);
        doctorRepository.save(doctor);
        return DoctorMapper.toResponseDTO(doctor);
    }

    public void eliminarDoctor(Long id) {
        Doctor doctor = buscarEntidadPorId(id);
        doctorRepository.delete(doctor);
    }

    public DoctorResponseDTO cambiarDisponibilidad(Long id, boolean disponible){
        Doctor doctor =  buscarEntidadPorId(id);
        doctor.setDisponible(disponible);
        doctorRepository.save(doctor);
        return DoctorMapper.toResponseDTO(doctor);
    }

    public List<DoctorResponseDTO> buscarPorEspecialidad(Especialidad especialidad){
        return doctorRepository.findByEspecialidad(especialidad).stream().map(DoctorMapper::toResponseDTO).toList();
    }




}
