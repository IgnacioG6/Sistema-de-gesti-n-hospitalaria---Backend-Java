package com.example.hospital.dto.response;

import java.time.LocalDate;

public record RecetaResponseDTO(
        Long id,
        Long historiaClinicaId,
        String pacienteNombre,
        String doctorNombre,
        List<PrescriptionItemDTO> medicamentos,
        String instruccionesGenerales,
        LocalDate fechaEmision,
        LocalDate validaHasta
) {}
