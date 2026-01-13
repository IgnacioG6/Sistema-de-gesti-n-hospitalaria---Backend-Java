package com.example.hospital.dto.request;
import java.time.LocalDateTime;

public record CitaRequestDTO(
        Long idPaciente,
        Long idDoctor,
        LocalDateTime fechaHora,
        String motivo,
        int duracion,
        String notas
) {
}
