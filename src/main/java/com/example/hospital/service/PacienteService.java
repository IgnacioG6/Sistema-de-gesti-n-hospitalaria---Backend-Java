package com.example.hospital.service;

import com.example.hospital.dto.request.PacienteRequestDTO;
import com.example.hospital.dto.response.PacienteResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.mapper.PacienteMapper;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.enums.Estado;
import com.example.hospital.repository.PacienteRepository;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<PacienteResponseDTO> obtenerTodos() {
        return pacienteRepository.findAll().stream()
                .map(PacienteMapper::toResponseDTO)
                .toList();
    }

    public Paciente buscarEntidadPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Paciente no encontrado con ID: " + id));
    }


    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = buscarEntidadPorId(id);
        return PacienteMapper.toResponseDTO(paciente);
    }

    public PacienteResponseDTO buscarPorDni(String dni) {
        Paciente paciente = pacienteRepository.findByDni(dni)
                .orElseThrow(() -> new EntidadNoEncontradaException("Paciente no encontrado con DNI: " + dni));
        return PacienteMapper.toResponseDTO(paciente);
    }

    public PacienteResponseDTO crearPaciente(PacienteRequestDTO dto) {
        Paciente paciente = PacienteMapper.toEntity(dto);
        pacienteRepository.save(paciente);
        return PacienteMapper.toResponseDTO(paciente);
    }

    public PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO dto) {
        Paciente paciente = buscarEntidadPorId(id);
        PacienteMapper.updateEntity(paciente, dto);
        pacienteRepository.save(paciente);
        return PacienteMapper.toResponseDTO(paciente);
    }

    public void eliminarPaciente(Long id) {
        Paciente paciente = buscarEntidadPorId(id);
        pacienteRepository.delete(paciente);
    }

    public PacienteResponseDTO cambiarEstado(Long id, Estado nuevoEstado) {
        Paciente paciente = buscarEntidadPorId(id);
        paciente.setEstado(nuevoEstado);
        pacienteRepository.save(paciente);

        return PacienteMapper.toResponseDTO(paciente);
    }
}
