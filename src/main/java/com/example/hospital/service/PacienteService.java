package com.example.hospital.service;

import com.example.hospital.dto.request.PacienteRequestDTO;
import com.example.hospital.dto.response.PacienteResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.mapper.PacienteMapper;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.enums.Estado;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PacienteService {

    private final List<Paciente> pacientes = new ArrayList<>();

    public List<PacienteResponseDTO> obtenerTodos() {
        return pacientes.stream()
                .map(PacienteMapper::toResponseDTO)
                .toList();
    }

    public Paciente buscarEntidadPorId(Long id) {
        return pacientes.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Paciente no encontrado con ID: " + id));
    }


    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente paciente = buscarEntidadPorId(id);
        return PacienteMapper.toResponseDTO(paciente);
    }

    public PacienteResponseDTO buscarPorDni(String dni) {
        return pacientes.stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .map(PacienteMapper::toResponseDTO)
                .orElseThrow(() -> new EntidadNoEncontradaException("Paciente no encontrado con dni: " + dni));
    }

    public PacienteResponseDTO crearPaciente(PacienteRequestDTO request) {
        Paciente paciente = new Paciente();
        paciente.setDni(request.dni());
        paciente.setNombre(request.nombre());
        paciente.setFechaNacimiento(request.fechaNacimiento());
        paciente.setGenero(request.genero());
        paciente.setEmail(request.email());
        paciente.setTelefono(request.telefono());
        paciente.setDireccion(request.direccion());
        paciente.setTipoSangre(request.tipoSangre());
        paciente.setAlergias(request.alergias());
        paciente.setEstado(Estado.ACTIVO);

        pacientes.add(paciente);
        return PacienteMapper.toResponseDTO(paciente);
    }

    public PacienteResponseDTO actualizarPaciente(Long id, PacienteRequestDTO request) {
        Paciente paciente = buscarEntidadPorId(id);

        paciente.setNombre(request.nombre());
        paciente.setFechaNacimiento(request.fechaNacimiento());
        paciente.setGenero(request.genero());
        paciente.setEmail(request.email());
        paciente.setTelefono(request.telefono());
        paciente.setDireccion(request.direccion());
        paciente.setTipoSangre(request.tipoSangre());
        paciente.setAlergias(request.alergias());

        return PacienteMapper.toResponseDTO(paciente);
    }

    public void eliminarPaciente(Long id) {
        Paciente paciente = buscarEntidadPorId(id);
        pacientes.remove(paciente);
    }

    public PacienteResponseDTO cambiarEstado(Long id, Estado nuevoEstado) {
        Paciente paciente = buscarEntidadPorId(id);
        paciente.setEstado(nuevoEstado);
        return PacienteMapper.toResponseDTO(paciente);
    }
}
