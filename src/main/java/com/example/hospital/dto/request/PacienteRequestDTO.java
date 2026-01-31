package com.example.hospital.dto.request;

import com.example.hospital.model.enums.Genero;
import com.example.hospital.model.enums.TipoSangre;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PacienteRequestDTO(
        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "^[0-9]{7,8}$", message = "El DNI debe tener entre 7 y 8 números")
        String dni,

        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotNull(message = "La fecha de nacimiento es obligatoria")
        @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
        LocalDate fechaNacimiento,

        @NotNull(message = "El género es obligatorio")
        Genero genero,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @Size(min = 1, max = 20, message = "El teléfono debe tener entre 1 y 20 caracteres")
        String telefono,

        @Size(min = 1, max = 200, message = "La dirección debe tener entre 1 y 200 caracteres")
        String direccion,

        @NotNull(message = "El tipo de sangre es obligatorio")
        TipoSangre tipoSangre,

        @Size(min = 1, max = 500, message = "El campo alergias debe tener entre 1 y 500 caracteres")
        String alergias
) {}