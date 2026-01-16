package com.example.hospital.service;

import com.example.hospital.dto.request.DoctorRequestDTO;
import com.example.hospital.dto.response.DoctorResponseDTO;
import com.example.hospital.exception.EntityNotFoundException;
import com.example.hospital.mapper.DoctorMapper;
import com.example.hospital.model.Doctor;
import com.example.hospital.model.enums.Especialidad;
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

    private Doctor buscarEntidadPorId(Long id) {
        return doctores.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Doctor no encontrado con id: " + id)
                );
    }


    public DoctorResponseDTO buscarDoctorPorId(Long id) {
        Doctor doctor = buscarEntidadPorId(id);
        return DoctorMapper.toResponseDTO(doctor);
    }

    public DoctorResponseDTO buscarDoctorPorLicencia(String licencia) {
        if (licencia == null || licencia.isBlank()) {
            throw new IllegalArgumentException("La licencia médica es obligatoria");
        }

        return doctores.stream()
                .filter(doctor -> licencia.equals(doctor.getLicenciaMedica()))
                .findFirst()
                .map(DoctorMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Doctor no encontrado con licencia: " + licencia
                ));
    }

    public DoctorResponseDTO registarDoctor(DoctorRequestDTO doctorRequestDTO) {
        Doctor doctor = new Doctor();
        doctor.setNombre(doctorRequestDTO.nombre());
        doctor.setLicenciaMedica(doctorRequestDTO.licenciaMedica());
        doctor.setEspecialidad(doctorRequestDTO.especialidad());
        doctor.setEmail(doctorRequestDTO.email());
        doctor.setTelefono(doctorRequestDTO.telefono());
        doctor.setAñosExperiencia(doctorRequestDTO.añosExperiencia());
        doctor.setHorarioAtencion(doctorRequestDTO.horarioAtencion());
        doctor.setDisponible(true);

        doctores.add(doctor);
        return DoctorMapper.toResponseDTO(doctor);
    }


    public DoctorResponseDTO actualizarDoctor(Long id, DoctorRequestDTO dto){
        Doctor doctor = buscarEntidadPorId(id);

        doctor.setNombre(dto.nombre());
        doctor.setLicenciaMedica(dto.licenciaMedica());
        doctor.setEspecialidad(dto.especialidad());
        doctor.setTelefono(dto.telefono());
        doctor.setEmail(dto.email());
        doctor.setAñosExperiencia(dto.añosExperiencia());
        doctor.setHorarioAtencion(dto.horarioAtencion());

        return DoctorMapper.toResponseDTO(doctor);
    }

    public void eliminarDoctor(Long id) {
        Doctor doctor = buscarEntidadPorId(id);
        doctores.remove(doctor);
    }

    public DoctorResponseDTO cambiarDisponibilidad(Long id, boolean disponible){
        Doctor doctor =  buscarEntidadPorId(id);
        doctor.setDisponible(disponible);
        return DoctorMapper.toResponseDTO(doctor);
    }

    public List<DoctorResponseDTO> buscarPorEspecialidad(Especialidad especialidad){
        return doctores.stream()
                .filter(doctor -> doctor.getEspecialidad().equals(especialidad))
                .map(DoctorMapper::toResponseDTO)
                .toList();
    }




}
