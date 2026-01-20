package com.example.hospital.mapper;

import com.example.hospital.dto.response.RecetaResponseDTO;
import com.example.hospital.model.Receta;

public class RecetaMapper {

    public static RecetaResponseDTO toResponseDTO(Receta receta) {
        if (receta == null) return null;

        return new RecetaResponseDTO(
                receta.getId(),
                receta.getHistoriaClinica().getId(),
                receta.getHistoriaClinica().getPaciente().getNombre(),
                receta.getHistoriaClinica().getDoctor().getNombre(),
                receta.getMedicamentos(),
                receta.getInstruccionesGenerales(),
                receta.getFechaEmision(),
                receta.getValidaHasta());
    }


}
