package com.example.hospital.dto.request;

import com.example.hospital.dto.RecetaItemDTO;

import java.time.LocalDate;
import java.util.List;

public record RecetaRequestDTO(
        Long historiaClinicaId,
        List<RecetaItemDTO> medicamentos,
        String instruccionesGenerales,
        LocalDate validaHasta
) {}
