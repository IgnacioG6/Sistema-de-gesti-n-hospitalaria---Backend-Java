package com.example.hospital.mapper;

import com.example.hospital.dto.RecetaItemDTO;
import com.example.hospital.dto.response.RecetaResponseDTO;
import com.example.hospital.model.Receta;

import java.util.List;

public class RecetaMapper {

    public static RecetaResponseDTO toResponseDTO(Receta receta) {
        if (receta == null) return null;

        List<RecetaItemDTO> medicamentosDTO = receta.getMedicamentos().stream()
                .map(item -> new RecetaItemDTO(
                        item.getMedicamento(),
                        item.getDosis(),
                        item.getFrecuencia(),
                        item.getDuracionDias(),
                        item.getInstrucciones()
                ))
                .toList();

        return new RecetaResponseDTO(
                receta.getId(),
                receta.getHistoriaClinica().getId(),
                receta.getHistoriaClinica().getPaciente().getNombre(),
                receta.getHistoriaClinica().getDoctor().getNombre(),
                medicamentosDTO,
                receta.getInstruccionesGenerales(),
                receta.getFechaEmision(),
                receta.getValidaHasta());
    }


}
