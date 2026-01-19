package com.example.hospital.mapper;

import com.example.hospital.dto.response.HistoriaClinicaResponseDTO;
import com.example.hospital.model.HistoriaClinica;

public class HistoriaClinicaMapper {

    public static HistoriaClinicaResponseDTO toResponseDTO(HistoriaClinica historiaClinica) {
        if (historiaClinica == null) return null;

        return new HistoriaClinicaResponseDTO(
                historiaClinica.getId(),
                historiaClinica.getPaciente().getId(),
                historiaClinica.getPaciente().getNombre(),
                historiaClinica.getPaciente().getDni(),
                historiaClinica.getDoctor().getId(),
                historiaClinica.getDoctor().getNombre(),
                historiaClinica.getDoctor().getEspecialidad(),
                historiaClinica.getCita().getId(),
                historiaClinica.getFecha(),
                historiaClinica.getDiagnostico(),
                historiaClinica.getSintomas(),
                historiaClinica.getTratamientoPrescrito(),
                historiaClinica.getObservaciones()


        );
    }

}
