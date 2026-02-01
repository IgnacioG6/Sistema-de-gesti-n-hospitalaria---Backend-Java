package com.example.hospital.mapper;

import com.example.hospital.dto.request.PacienteRequestDTO;
import com.example.hospital.dto.response.PacienteResponseDTO;
import com.example.hospital.model.Paciente;
import com.example.hospital.model.enums.Estado;

public class PacienteMapper {

    public static PacienteResponseDTO toResponseDTO(Paciente paciente) {
        if (paciente == null) return null;

        return new PacienteResponseDTO(
                paciente.getId(),
                paciente.getDni(),
                paciente.getNombre(),
                paciente.getFechaNacimiento(),
                paciente.getGenero(),
                paciente.getEmail(),
                paciente.getTelefono(),
                paciente.getDireccion(),
                paciente.getTipoSangre(),
                paciente.getAlergias(),
                paciente.getEstado());
    }

    public static Paciente toEntity(PacienteRequestDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setDni(dto.dni());
        paciente.setNombre(dto.nombre());
        paciente.setFechaNacimiento(dto.fechaNacimiento());
        paciente.setGenero(dto.genero());
        paciente.setEmail(dto.email());
        paciente.setTelefono(dto.telefono());
        paciente.setDireccion(dto.direccion());
        paciente.setTipoSangre(dto.tipoSangre());
        paciente.setAlergias(dto.alergias());
        paciente.setEstado(Estado.ACTIVO);
        return paciente;
    }

    public static void updateEntity(Paciente paciente, PacienteRequestDTO dto) {
        paciente.setNombre(dto.nombre());
        paciente.setFechaNacimiento(dto.fechaNacimiento());
        paciente.setGenero(dto.genero());
        paciente.setEmail(dto.email());
        paciente.setTelefono(dto.telefono());
        paciente.setDireccion(dto.direccion());
        paciente.setTipoSangre(dto.tipoSangre());
        paciente.setAlergias(dto.alergias());
    }
}