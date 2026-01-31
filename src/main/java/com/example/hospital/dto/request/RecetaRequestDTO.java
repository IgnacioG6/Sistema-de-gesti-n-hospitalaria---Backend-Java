package com.example.hospital.dto.request;

import com.example.hospital.dto.RecetaItemDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record RecetaRequestDTO(
        @NotNull(message = "El ID de la historia clínica es obligatorio")
        Long historiaClinicaId,

        @NotEmpty(message = "La receta debe contener al menos un medicamento")
        @Valid
        List<RecetaItemDTO> medicamentos,

        @NotBlank(message = "Las instrucciones generales son obligatorias")
        @Size(max = 1000, message = "Las instrucciones no pueden exceder los 1000 caracteres")
        String instruccionesGenerales,

        @NotNull(message = "La fecha de validez es obligatoria")
        LocalDate validaHasta
) {}
