package com.example.hospital.dto.response;

import com.example.hospital.dto.RecetaItemDTO;

import java.time.LocalDate;
import java.util.List;

public record RecetaResponseDTO(
        Long id,
        Long historiaClinicaId,
        String pacienteNombre,
        String doctorNombre,
        List<RecetaItemDTO> medicamentos,
        String instruccionesGenerales,
        LocalDate fechaEmision,
        LocalDate validaHasta
) {}
