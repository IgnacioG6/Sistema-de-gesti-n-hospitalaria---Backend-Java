package com.example.hospital.mapper;

import com.example.hospital.dto.RecetaItemDTO;
import com.example.hospital.dto.request.RecetaRequestDTO;
import com.example.hospital.dto.response.RecetaResponseDTO;
import com.example.hospital.model.HistoriaClinica;
import com.example.hospital.model.Receta;
import com.example.hospital.model.RecetaItem;

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

    public static Receta toEntity(RecetaRequestDTO dto, HistoriaClinica historia) {
        Receta receta = new Receta();
        receta.setHistoriaClinica(historia);
        receta.setInstruccionesGenerales(dto.instruccionesGenerales());
        receta.setValidaHasta(dto.validaHasta());

        dto.medicamentos().forEach(itemDTO -> {
            RecetaItem item = new RecetaItem();
            item.setReceta(receta);
            item.setMedicamento(itemDTO.medicamento());
            item.setDosis(itemDTO.dosis());
            item.setFrecuencia(itemDTO.frecuencia());
            item.setDuracionDias(itemDTO.duracionDias());
            item.setInstrucciones(itemDTO.instrucciones());
            receta.getMedicamentos().add(item);
        });

        return receta;
    }


}
