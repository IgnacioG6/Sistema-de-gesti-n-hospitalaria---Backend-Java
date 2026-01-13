package com.example.hospital.dto.request;

import com.example.hospital.model.enums.Genero;
import com.example.hospital.model.enums.TipoSangre;

import java.time.LocalDate;

public record PacienteRequestDTO(
        String dni,
        String nombre,
        LocalDate fechaNacimiento,
        Genero genero,
        String email,
        String telefono,
        String direccion,
        TipoSangre tipoSangre,
        String alergias

) {}