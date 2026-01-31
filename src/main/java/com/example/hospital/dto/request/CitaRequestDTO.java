package com.example.hospital.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record CitaRequestDTO(
        @NotNull(message = "El ID del paciente es obligatorio")
        Long idPaciente,

        @NotNull(message = "El ID del doctor es obligatorio")
        Long idDoctor,

        @NotNull(message = "La fecha y hora son obligatorias")
        LocalDateTime fechaHora,

        @NotBlank(message = "El motivo de la consulta es obligatorio")
        @Size(min = 5, max = 255, message = "El motivo debe tener entre 5 y 255 caracteres")
        String motivo,

        @Min(value = 15, message = "La duración mínima de una cita es de 15 minutos")
        @Max(value = 120, message = "La duración máxima de una cita es de 120 minutos")
        int duracion,

        @Size(max = 500, message = "Las notas no pueden exceder los 500 caracteres")
        String notas
) {
}
