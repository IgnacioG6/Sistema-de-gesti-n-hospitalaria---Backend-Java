package com.example.hospital.dto.response;

import com.example.hospital.model.enums.Estado;
import com.example.hospital.model.enums.Genero;
import com.example.hospital.model.enums.TipoSangre;

import java.time.LocalDate;

public record PacienteResponseDTO(
        Long id,
        String dni,
        String nombre,
        LocalDate fechaNacimiento,
        Genero genero,
        String email,
        String telefono,
        String direccion,
        TipoSangre tipoSangre,
        String alergias,
        Estado estado

) {}