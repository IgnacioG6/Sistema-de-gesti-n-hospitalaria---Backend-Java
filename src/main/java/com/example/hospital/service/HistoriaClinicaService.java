package com.example.hospital.service;

import com.example.hospital.dto.request.HistoriaClinicaRequestDTO;
import com.example.hospital.dto.response.HistoriaClinicaResponseDTO;
import com.example.hospital.exception.EntidadNoEncontradaException;
import com.example.hospital.exception.ValidacionException;
import com.example.hospital.mapper.HistoriaClinicaMapper;
import com.example.hospital.model.Cita;
import com.example.hospital.model.HistoriaClinica;
import com.example.hospital.model.enums.EstadoCita;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoriaClinicaService {

    private final CitaService citaService;
    private final List<HistoriaClinica> historias = new ArrayList<>();


    public HistoriaClinicaService(CitaService citaService) {
        this.citaService = citaService;
    }

    public HistoriaClinicaResponseDTO crearHistoriaClinica(HistoriaClinicaRequestDTO request){
        Cita cita = citaService.buscarEntidadPorId(request.idCita());

        if (cita.getEstadoCita() != EstadoCita.COMPLETADO) {
            throw new ValidacionException("Solo se pueden crear historias de citas completadas");
        }

        boolean yaTieneHistoria = historias.stream()
                .anyMatch(historia -> historia.getCita().getId().equals(request.idCita()));

        if (yaTieneHistoria) {
            throw new ValidacionException("La cita ya tiene una historia clínica");
        }

        HistoriaClinica historiaClinica = new HistoriaClinica();
        historiaClinica.setDoctor(cita.getDoctor());
        historiaClinica.setPaciente(cita.getPaciente());
        historiaClinica.setCita(cita);
        historiaClinica.setFecha(LocalDateTime.now());
        historiaClinica.setDiagnostico(request.diagnostico());
        historiaClinica.setSintomas(request.sintomas());
        historiaClinica.setTratamientoPrescrito(request.tratamientoPrescrito());
        historiaClinica.setObservaciones(request.observaciones());

        historias.add(historiaClinica);

        return HistoriaClinicaMapper.toResponseDTO(historiaClinica);
    }


    public List<HistoriaClinicaResponseDTO> listarHistorias(){
        return historias.stream().map(HistoriaClinicaMapper::toResponseDTO).toList();
    }

    public HistoriaClinicaResponseDTO buscarEntidadPorId(Long id) {
        return historias.stream()
                .filter(historiaClinica -> historiaClinica.getId().equals(id))
                .findFirst()
                .map(HistoriaClinicaMapper::toResponseDTO)
                .orElseThrow(() -> new EntidadNoEncontradaException("Historia clinica no encontrada con ID: " + id));
    }


    public List<HistoriaClinicaResponseDTO> buscarPorPaciente(Long id){
        return historias.stream()
                .filter(historiaClinica -> historiaClinica.getPaciente().getId().equals(id))
                .map(HistoriaClinicaMapper::toResponseDTO)
                .toList();
    }

    public List<HistoriaClinicaResponseDTO> buscarPorDoctor(Long doctorId) {
        return historias.stream()
                .filter(h -> h.getDoctor().getId().equals(doctorId))
                .map(HistoriaClinicaMapper::toResponseDTO)
                .toList();
    }



}
