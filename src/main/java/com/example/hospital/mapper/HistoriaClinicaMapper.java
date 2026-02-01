package com.example.hospital.mapper;

import com.example.hospital.dto.request.ActualizarHistoriaClinicaRequestDTO;
import com.example.hospital.dto.request.HistoriaClinicaRequestDTO;
import com.example.hospital.dto.response.HistoriaClinicaResponseDTO;
import com.example.hospital.model.Cita;
import com.example.hospital.model.HistoriaClinica;

import java.time.LocalDateTime;

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
    public static HistoriaClinica toEntity(Cita cita, HistoriaClinicaRequestDTO dto) {
        HistoriaClinica historia = new HistoriaClinica();
        historia.setPaciente(cita.getPaciente());
        historia.setDoctor(cita.getDoctor());
        historia.setCita(cita);
        historia.setFecha(LocalDateTime.now());
        historia.setDiagnostico(dto.diagnostico());
        historia.setSintomas(dto.sintomas());
        historia.setTratamientoPrescrito(dto.tratamientoPrescrito());
        historia.setObservaciones(dto.observaciones());
        return historia;
    }

    public static void updateEntity(HistoriaClinica historia, ActualizarHistoriaClinicaRequestDTO dto) {
        historia.setDiagnostico(dto.diagnostico());
        historia.setSintomas(dto.sintomas());
        historia.setTratamientoPrescrito(dto.tratamientoPrescrito());
        historia.setObservaciones(dto.observaciones());
    }

}
