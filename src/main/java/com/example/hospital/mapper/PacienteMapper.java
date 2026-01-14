package com.example.hospital.mapper;

import com.example.hospital.dto.response.PacienteResponseDTO;
import com.example.hospital.model.Paciente;

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
}